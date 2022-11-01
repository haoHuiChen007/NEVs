package com.example.nevs.module.car.controller;

import com.example.nevs.common.R;
import com.example.nevs.module.car.entity.Params;
import com.example.nevs.module.car.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/car")
public class CarController {

    @Autowired
    private ICarService carService;

    @GetMapping("/getCarList")
    public R getCarList(@RequestParam Map<String,String> map){
        return carService.getCarList(map);
    }

    @GetMapping("/getParams")
    public R getParams(){
        return carService.getParams();
    }

    @PostMapping("/insertParams")
    public R insertParams(@RequestBody Params params){
        return carService.insertParams(params);
    }

    @GetMapping("/hot")
    public R hot(){
        return carService.hot();
    }

    @PostMapping("/show")
    public R show(@RequestBody Map<String,String> map){
        return carService.show(map);
    }
    @GetMapping("/getCarDetail")
    public R getCarDetail(@RequestParam String id){
        return carService.getCarDetail(id);
    }
}
