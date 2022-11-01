package com.example.nevs.module.collection.dao;


import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.collection.entity.CollectionInfo;

import java.util.List;

public interface CollectionDao {
    //通过userId找CollectionInfo
    List<CollectionInfo> findCollectionById(String userId);

    //通过carId寻找car
    Car findCarById(String carId);
}
