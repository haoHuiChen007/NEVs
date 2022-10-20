package com.example.nevs;

import com.example.nevs.module.user.entity.Role;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.module.user.service.IRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class NeVsApplicationTests {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;

    @Test
    void contextLoads() {
        Map<String,String> admin = new HashMap<>();
        admin.put("username","Admin");
        admin.put("password","Admin");
        admin.put("realName","陈浩辉");
        admin.put("email","2954390791@aa.com");
        admin.put("roleId","6350ecf45f5a514f4891cf47");
        System.out.println(adminService.addAdmin(admin));
    }
    @Test
    void testLogin(){
        Map<String,String> login = new HashMap<>();
        login.put("username","Admin");
        login.put("password","Admin");
        System.out.println(adminService.loginAdmin(login));
    }
    @Test
    void createRole(){
        Role role = new Role();
        role.setRoleName("管理员");
    roleService.createRole(role);
    }

}
