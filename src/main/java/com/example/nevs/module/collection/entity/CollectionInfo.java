package com.example.nevs.module.collection.entity;

import com.example.nevs.module.car.entity.Car;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document("co_collection_info")
public class CollectionInfo {
    private String id;
    private String collectionName;
    @DBRef
    private List<Car> car;
}
