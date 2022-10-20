package com.example.nevs.module.user.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.nevs.common.PageRequest;
import com.example.nevs.common.R;
import com.example.nevs.module.user.entity.Admin;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class AdminController {
    @Autowired
    private IAdminService adminService;
/*添加管理员用户*/
    @PostMapping("/addAdmin")
    public R addAdmin(@RequestBody Map<String,String> admin){
        return adminService.addAdmin(admin);
    }
    /*管理员登入*/
    @PostMapping("/loginAdmin")
    public R loginAdmin(@RequestParam Map<String,String> login){
        return adminService.loginAdmin(login);
    }
    /*修改管理员个人信息*/
    @PostMapping("/updateSelf")
    public R updateSelf(@RequestBody Map<String,String> update, HttpServletRequest request){

        Map<String, String> jwtPayload = jwtPayload(request);
        update.put("id",jwtPayload.get("id"));
        return adminService.updateSelf(update);
    }
    @GetMapping("/deleteAdmin")
    public R deleteAdmin(@RequestParam List<Admin> admins, HttpServletRequest request){

        Map<String, String> jwtPayload = jwtPayload(request);
        List<String> ids = admins.stream().filter(admin -> !Objects.equals(admin.getUser().getUsername(), "admin")).map(Admin::getId).collect(Collectors.toList());
        return adminService.deleteAdmin(jwtPayload.get("id"),ids);
    }
    /*获取管理员和用户*/
    @GetMapping("/getUserList")
    public R getUserList(@RequestParam PageRequest pageRequest, HttpServletRequest request){
        Map<String, String> jwtPayload = jwtPayload(request);
        return adminService.getUserList(pageRequest, jwtPayload.get("roleId"));
    }
    /*通过head获取信息*/
    public Map<String,String> jwtPayload(HttpServletRequest request){
        Map<String,String> jwtPayload = new HashMap<>();
        String token=request.getHeader("token");
        DecodedJWT decodedJWT = JwtUtil.verify(token);
        String id=decodedJWT.getClaim("id").asString();
        String username=decodedJWT.getClaim("username").asString();
        String email=decodedJWT.getClaim("email").asString();
        String phone=decodedJWT.getClaim("phone").asString();
        jwtPayload.put("id",id);
        jwtPayload.put("username",username);
        jwtPayload.put("email",email);
        jwtPayload.put("phone",phone);
        return jwtPayload;
    }
}
