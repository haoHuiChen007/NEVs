package com.example.nevs.module.user.controller;

import com.example.nevs.common.R;
import com.example.nevs.module.user.entity.Role;
import com.example.nevs.module.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @GetMapping("/getRoleList")
    R getRoleList(){
        return roleService.getRoleList();
    }
    @PostMapping("/createRole")
    R createRole(@RequestBody Role role){
        return roleService.createRole(role);
    }
}
