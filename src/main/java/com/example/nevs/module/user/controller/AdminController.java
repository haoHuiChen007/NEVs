package com.example.nevs.module.user.controller;

import com.example.nevs.common.Constants;
import com.example.nevs.common.PageRequest;
import com.example.nevs.common.R;
import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.serve.sys.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class AdminController {
    @Autowired
    private IAdminService adminService;


    /*添加管理员用户*/
    @PostMapping("/addAdmin")
    @Log(module = "管理员模块", type = Constants.ADD,desc = "添加管理员用户")
    public R addAdmin(@RequestBody Map<String, String> admin) {
        return adminService.addAdmin(admin);
    }

    /*管理员登入*/
    @PostMapping("/loginAdmin")
    public R loginAdmin(@RequestBody Map<String, String> login) {
        return adminService.loginAdmin(login);
    }

    /*修改管理员个人信息*/
    @PostMapping("/updateSelf")
    @Log(module = "管理员模块", type = Constants.UPDATE,desc = "修改自己信息")
    public R updateSelf(@RequestBody Map<String, String> update, HttpServletRequest request) {
        Map<String, String> jwtPayload = adminService.jwtPayload(request);
        update.put("id", jwtPayload.get("id"));
        return adminService.updateSelf(update);
    }

    @GetMapping("/deleteAdmin/{id}")
    @Log(module = "管理员模块", type = Constants.DELETE, desc = "获取管理员和用户")
    public R deleteAdmin(HttpServletRequest request, @PathVariable String id) {
        Map<String, String> jwtPayload = adminService.jwtPayload(request);
//        List<String> ids = admins.stream().filter(admin -> !Objects.equals(admin.getUsername(), "Admin")).map(User::getId).collect(Collectors.toList());
        return adminService.deleteAdmin(jwtPayload.get("id"), id);
    }

    /*获取管理员和用户*/
    @Log(module = "管理员模块", type = Constants.SELECT, desc = "获取管理员和用户")
    @GetMapping("/getUserList")
    public R getUserList(@RequestParam Integer page, @RequestParam Integer size,@RequestParam(required = false) String username, HttpServletRequest request) {
        Map<String, String> jwtPayload = adminService.jwtPayload(request);
        PageRequest pageRequest = new PageRequest(page, size);
        return adminService.getUserList(pageRequest, jwtPayload.get("roleId"),username);
    }

    /*通过head获取信息*/
    @PostMapping("/insertCar")
    @Log(module = "管理员模块", type = Constants.INSERT,desc = "添加车")
    public R insertCar(@RequestBody  Car car) {
        return adminService.insertCar(car);
    }

    @Log(module = "管理员模块",type = Constants.SELECT,desc = "通过id获取车信息")
    @GetMapping("/getCarById/{id})")
    public R getCarById(@PathVariable String id){
        return adminService.getCarById(id);
    }

    @PostMapping("/getCarList")
    @Log(module = "车列表模块",type = Constants.SELECT, desc = "获取车的列表页面")
    public R getCarList(@RequestBody Map<String,String> map){
        return adminService.getCarList(map);
    }
    @PostMapping("/updateCarById")
    @Log(module = "车列表模块",type = Constants.SELECT, desc = "获取车的列表页面")
    public R updateCarById(@RequestBody Car car){
        return adminService.updateCarById(car);
    }

    @GetMapping("/deleteCarById/{id}")
    public R deleteCarById(@PathVariable String id){
        return adminService.deleteCarById(id);
    }
    @GetMapping("/getNum")
    public R getCarNum(){
        return adminService.getNum();
    }
}
