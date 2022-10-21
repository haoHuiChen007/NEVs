package com.example.nevs.module.car.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Car {
    private String id;
    private Integer carType;
    private String carName;
    private String brand;
    private Double price;
    @DBRef
    private CarParams carParams;

}
