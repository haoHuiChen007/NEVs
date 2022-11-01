package com.example.nevs.module.car.service;

import com.example.nevs.common.R;
import com.example.nevs.module.car.entity.Params;

import java.util.Map;

public interface ICarService {
    R getCarList(Map<String, String> map);

    R getParams();

    R insertParams(Params params);

    R hot();

    R show(Map<String, String> map);

    R getCarDetail(String id);
}
