package com.example.nevs.module.user.controller;

import com.example.nevs.common.R;
import com.example.nevs.module.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @GetMapping("/getRoleList")
    R getRoleList(){
        return roleService.getRoleList();
    }
    @GetMapping("/createRole")
    R createRole(@RequestParam String roleName){
        return roleService.createRole(roleName);
    }
    @GetMapping("/deleteRole/{id}")
    R deleteRole(@PathVariable String id){
        return roleService.deleteRole(id);

    }
}
