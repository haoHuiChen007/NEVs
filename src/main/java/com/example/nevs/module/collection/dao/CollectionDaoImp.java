package com.example.nevs.module.collection.dao;

import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.collection.entity.CollectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectionDaoImp implements CollectionDao{
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<CollectionInfo> findCollectionById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        List<CollectionInfo> all = mongoTemplate.find(query, CollectionInfo.class);
        return all;
    }

    @Override
    public Car findCarById(String carId) {
        return mongoTemplate.findById(carId, Car.class);
    }
}
