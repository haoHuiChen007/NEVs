package com.example.nevs.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.example.nevs.common.PageResult;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class IPageHelper {
    @Resource
    private MongoTemplate mongoTemplate;
    public static final int FIRST_PAGE_NUM = 0;
    public static final String ID = "_id";

    /*分页查询*/
    public <T> PageResult<T> pageQuery(Query query, Class<T> entityClass, Integer size, Integer page) {
        return pageQuery(query, entityClass, Function.identity(), size, page, null);
    }

    public <T, R> PageResult<R> pageQuery(Query query, Class<T> entityClass, Function<T, R> mapper, Integer size, Integer page) {
        return pageQuery(query, entityClass, mapper, size, page, null);
    }

    public <T, R> PageResult<R> pageQuery(Query query, Class<T> entityClass, Function<T, R> mapper, Integer size, Integer page, String lastId) {
        //分页逻辑
        int total = Math.toIntExact(mongoTemplate.count(query, entityClass));
        int pages = (int) Math.ceil(total / (double) size);
        if (pages < 0 || page > pages) {
            page = FIRST_PAGE_NUM;
        }
        Criteria criteria = new Criteria();
        if (StrUtil.isNotBlank(lastId)) {
            if (pages != FIRST_PAGE_NUM) {
                criteria.and(ID).gt(new ObjectId(lastId));
            }
            query.limit(size);
            query.addCriteria(criteria);
        } else {
            int skip = size * page;
            query.skip(skip).limit(size);
        }//CollUtil.newArrayList(new Sort.Order(Sort.Direction.DESC,"date")).toArray()
        List<T> entityList = mongoTemplate.find(query.with(Sort.by(CollUtil.newArrayList(new Sort.Order(Sort.Direction.DESC, "createTime")))), entityClass);
        PageResult<R> pageResult = new PageResult<>();
        pageResult.setPage(page)
                .setSize(size)
                .setTotalPages(pages)
                .setTotalElements(total)
                .setContent(entityList.stream().map(mapper).collect(Collectors.toList()));
        return pageResult;
    }

    public Map<String,Object> getNotNullProperties(Object source){
        BeanWrapper src=new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Map<String,Object> notEmpty=new HashMap<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue=src.getPropertyValue(pd.getName());
            if (srcValue!=null && !Objects.equals(pd.getName(),"class")&& !pd.getName().equals("id")&& !pd.getName().equals("password") && !pd.getName().equals("empty"))
                notEmpty.put(pd.getName(), srcValue);
        }
        return notEmpty;
    }

    public Update getUpdateSet(Object source){
        Map<String,Object> notNullProperties=getNotNullProperties(source);
        Update update=new Update();
        for (String s : notNullProperties.keySet()) {
            update.set(s,notNullProperties.get(s));
        }
        return update;
    }

}
