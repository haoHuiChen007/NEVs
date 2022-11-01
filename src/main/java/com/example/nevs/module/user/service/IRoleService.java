package com.example.nevs.module.user.service;

import com.example.nevs.common.R;
import com.example.nevs.module.user.entity.Role;

public interface IRoleService {
    R getRoleList();

    Role getRoleNameById(String id);

    R createRole(String roleName);

    R deleteRole(String id);
}
