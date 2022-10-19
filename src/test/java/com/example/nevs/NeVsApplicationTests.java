package com.example.nevs;

import com.example.nevs.module.user.service.IAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class NeVsApplicationTests {
    @Autowired
    private IAdminService adminService;

    @Test
    void contextLoads() {
        Map<String,String> admin = new HashMap<>();
        admin.put("username","sfasf");
        admin.put("password","12345");
        admin.put("email","2954390791@aa.com");
        admin.put("roleId","aaasdfa");
        System.out.println(adminService.addAdmin(admin));
    }

}
