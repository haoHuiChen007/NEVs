package com.example.nevs.module.collection.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.nevs.common.E;
import com.example.nevs.common.R;
import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.collection.dao.CollectionDao;
import com.example.nevs.module.collection.entity.CollectionInfo;
import com.example.nevs.module.collection.ex.*;
import com.example.nevs.module.collection.service.ICollectionService;
import com.example.nevs.util.ListPagHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CollectionImpl implements ICollectionService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CollectionDao collectionDao;
    @Override
    public R deleteByCarId(Map<String, String> map) {
        Query query = getDeleteQuery(map);
        CollectionInfo andRemove = mongoTemplate.findAndRemove(query, CollectionInfo.class);
        if (BeanUtil.isNotEmpty(andRemove)){
            return R.success();
        }
        return R.error(E.DELETE_ERROR);
    }

    @Override
    public R createCollection(Map<String, String> map) {
        CollectionInfo collectionEntity = createCollectionEntity(map);
        CollectionInfo insert = mongoTemplate.insert(collectionEntity);
        if (BeanUtil.isNotEmpty(insert)){
            return R.success();
        }
        return R.error(E.INSERT_ERROR);
    }
    private CollectionInfo createCollectionEntity(Map<String,String> map){
        CollectionInfo collectionInfo = new CollectionInfo();
        String userId = map.get("userId");
        String collectionName = map.get("collectionName");
        collectionInfo.setCollectionName(collectionName).setUserId(userId);
        return collectionInfo;
    }
    private Query getDeleteQuery(Map<String,String> map){
        Query query = new Query();
        String userId = map.get("userId");
        String id = map.get("id");
        String carId = map.get("carId");
        query.addCriteria(Criteria.where("_id").is(id).and("cars.id").is(carId));
        return query;
    }

    //收藏小窗口
    @Override
    public List<CollectionInfo> selectCollection(String userId) {
        List<CollectionInfo> all = collectionDao.findCollectionById(userId);
        if (all == null) {
            throw new CollectionInfoNotFoundException("收藏夹查找异常");
        }
        return all;
    }

    //创建收藏夹
    @Override
    public void insertCollection(String userId, String collectionName) {
        if (userId == null) {
            throw new CollectionUserIdException("用户名获取异常");
        }
        List<CollectionInfo> allList = collectionDao.findCollectionById(userId);
        if (allList.stream()
                .filter(item -> item.getCollectionName()
                        .equals(collectionName))
                .findAny()
                .isPresent()) {
            throw new CollectionNameException("名字重复");
        }
        CollectionInfo collectionInfo = new CollectionInfo();
        collectionInfo.setCollectionName(collectionName);
        collectionInfo.setUserId(userId);
        CollectionInfo insert = mongoTemplate.insert(collectionInfo);
        if (insert == null) {
            throw new CollectionInsertException("创建异常");
        }
    }

    //删除收藏夹
    @Override
    public void deleteCollection(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, CollectionInfo.class);
    }

    //修改文件夹名字
    @Override
    public void updateCollectionName(String id, String collectionName) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("collectionName", collectionName);
        mongoTemplate.upsert(query, update, CollectionInfo.class);
    }


    //添加收藏
    @Override
    public void insertCarToCollection(String id, String carId) {
        Car car = collectionDao.findCarById(carId);
        if (car == null) {
            throw new CollectionCarNotFoundException("车辆查询异常");
        }
        CollectionInfo collectionInfo = mongoTemplate.findById(id, CollectionInfo.class);
        List<Car> list = collectionInfo.getCars();
        list.add(car);
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("car", list);
        mongoTemplate.upsert(query, update, CollectionInfo.class);
    }

    //取消收藏
    @Override
    public void removeCarFromCollection(String id, String carId) {
        Car car = collectionDao.findCarById(carId);
        if (car == null) {
            throw new CollectionCarNotFoundException("车辆查询异常");
        }
        CollectionInfo collectionInfo = mongoTemplate.findById(id, CollectionInfo.class);
        List<Car> list = collectionInfo.getCars();
        list.remove(car);
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("car", list);
        mongoTemplate.upsert(query, update, CollectionInfo.class);
    }

    //展示收藏
    @Override
    public List<Car> displayCollection(Integer pageIndex, Integer pageSize, String id){
        Query query = new Query(Criteria.where("_id").is(id));
        CollectionInfo info = mongoTemplate.findOne(query, CollectionInfo.class);
        List<Car> car = info.getCars();
        ListPagHelper listPagHelper = new ListPagHelper(car, pageIndex, pageSize);
        List dataList = listPagHelper.getDataList();
        return dataList;
    }
}
