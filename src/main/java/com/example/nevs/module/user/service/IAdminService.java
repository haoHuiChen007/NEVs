package com.example.nevs.module.user.service;

import com.example.nevs.common.PageRequest;
import com.example.nevs.common.R;
import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.user.entity.User;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IAdminService {
    R addAdmin (Map<String,String> admin);

    R loginAdmin(Map<String, String> login);

    R updateSelf(Map<String, String> update);

    User getAdminById(String id);

    R deleteAdmin(String selfId, String id);

    R getUserList(PageRequest pageRequest, String roleId,String username);

    User getAdminByUserId(String id);

    R insertCar(Car car);

    Map<String,String> jwtPayload(HttpServletRequest request);

    R getCarById(String id);

    R getCarList(Map<String,String>map);

    R updateCarById(Car car);

    R deleteCarById(String id);

    User getUserByUsername(String username);

    R getNum();
}
