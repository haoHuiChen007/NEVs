package com.example.nevs.module.collection.service;

import com.example.nevs.common.R;
import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.collection.entity.CollectionInfo;

import java.util.List;
import java.util.Map;

public interface ICollectionService {
    R deleteByCarId(Map<String, String> map);

    R createCollection(Map<String, String> map);

    //查找collection
    List<CollectionInfo> selectCollection(String userId);
    //创建收藏夹
    void insertCollection(String userId,String collectionName);
    //删除收藏夹
    void deleteCollection(String id);
    //修改文件夹名字
    void updateCollectionName(String id,String collectionName);



    //添加收藏
    void insertCarToCollection(String id,String carId);
    //取消收藏
    void removeCarFromCollection(String id,String carId);
    //在收藏夹中展示
    public List<Car> displayCollection(Integer pageIndex, Integer pageSize, String id);
}
