package com.example.nevs.module.user.controller;

import com.example.nevs.common.R;
import com.example.nevs.module.user.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class AdminController {
    @Autowired
    private IAdminService adminService;
/*添加管理员用户*/
    @PostMapping("/addAdmin")
    public R addAdmin(@RequestParam Map<String,String> admin){
        return adminService.addAdmin(admin);
    }
    /*管理员登入*/
    @PostMapping("/loginAdmin")
    public R loginAdmin(@RequestParam Map<String,String> login){
        return adminService.loginAdmin(login);

    }
}
