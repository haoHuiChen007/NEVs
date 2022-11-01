package com.example.nevs.module.car.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.example.nevs.common.E;
import com.example.nevs.common.PageResult;
import com.example.nevs.common.R;
import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.car.entity.Params;
import com.example.nevs.module.car.service.ICarService;
import com.example.nevs.module.comment.emtity.Comment;
import com.example.nevs.module.comment.service.ICommentService;
import com.example.nevs.util.IPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl implements ICarService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IPageHelper pageHelper;
    @Autowired
    private ICommentService commentService;
    @Override
    public R getCarList(Map<String, String> map) {
        Query query = getCarQuery(map);
        List<Car> cars = mongoTemplate.find(query, Car.class);
        return R.success(cars);
    }


    @Override
    public R getParams() {
        Query query =new Query();
        Params params = mongoTemplate.findOne(query, Params.class);
        return R.success(params);
    }

    @Override
    public R insertParams(Params params) {
        Params insert = mongoTemplate.insert(params);
        if (BeanUtil.isEmpty(insert)){
            return R.error(E.INSERT_ERROR);
        }
        return R.success();
    }

    @Override
    public R hot() {
        Query query = new Query();
        PageResult<Car> carPageResult = pageHelper.pageQuery(query, Car.class, 5, 1);
        return R.success(carPageResult);
    }

    @Override
    public R show( Map<String, String> map) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> result= new HashMap<>();
        Query carQuery = getCarQuery(map);
        PageResult<Car> carPageResult = pageHelper.pageQuery(carQuery, Car.class, Integer.valueOf(map.get("size")), Integer.valueOf(map.get("page")));
        for (Car car : carPageResult.getContent()) {
            Map<String,Object> one = new HashMap<>();
            Comment comment = commentService.getOneById(car.getId());
            one.put("car",car);
            one.put("comment",comment);
            resultList.add(one);
        }
        result.put("list",resultList);
        carPageResult.setContent(null);
        result.put("pageInfo",carPageResult);
        return R.success(result);
    }

    @Override
    public R getCarDetail(String id) {
        Car car = mongoTemplate.findById(id, Car.class);
        return R.success(car);
    }


    private Query getCarQuery(Map<String,String> map){
        Query query = new Query();
        String carType = map.get("carType");
        String brand = map.get("brand");
        String carName = map.get("carName");
        String priceMax = map.get("priceMax");
        String priceMin = map.get("priceMin");
        if (StrUtil.isNotBlank(carType)){
            query.addCriteria(Criteria.where("carType").is(carType));
        }if(StrUtil.isNotBlank(brand)){
            query.addCriteria(Criteria.where("brand").is(brand));
        }if (StrUtil.isNotBlank(carName)){
            query.addCriteria(Criteria.where("carName").is(carName));
        }if (StrUtil.isNotBlank(priceMax)){
            query.addCriteria(Criteria.where("price").lte(Double.valueOf(priceMax)));
        }if (StrUtil.isNotBlank(priceMin)){
            query.addCriteria(Criteria.where("priceMin").gte(Double.valueOf(priceMin)));
        }
        return query;
    }
}
