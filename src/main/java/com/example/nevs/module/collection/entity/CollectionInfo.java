package com.example.nevs.module.collection.entity;

import com.example.nevs.module.car.entity.Car;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document("co_collection_info")
@Accessors(chain = true)
@Data
public class CollectionInfo {
    private String id;
    private String userId;
    private String collectionName;
    @DBRef
    private List<Car> cars;
}
