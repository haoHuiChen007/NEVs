package com.example.nevs.module.car.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("co_params")
@Data
@Accessors(chain = true)
public class Params {
    private String id;
    private List<String> colors;
    private List<String> carTypes;
    private List<String> brands;
    private List<String> engines;
    private List<String> gearboxes;
}
