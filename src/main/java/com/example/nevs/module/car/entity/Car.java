package com.example.nevs.module.car.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@Document("co_car")
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    private static final long serialVersionUID = -1242493306307174690L;
    private String id;
    private String carType;
    private String carName;
    private String brand;
    private Double price;
    private List<String> carImages;
    private String color;
    private String engine;
    private String fuelConsumption;
    private String gearbox;
    private String horsepower;
    private String maxSpeed;
}
