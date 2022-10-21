package com.example.nevs.module.collection.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document("co_collection")
public class Collection {
    private String id;
    private String userId;
    @DBRef
    private List<CollectionInfo> collectionInfos;
}
