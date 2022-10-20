package com.example.nevs.module.user.service;

import com.example.nevs.common.PageRequest;
import com.example.nevs.common.R;
import com.example.nevs.module.user.entity.Admin;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    R addAdmin (Map<String,String> admin);

    R loginAdmin(Map<String, String> login);

    R updateSelf(Map<String, String> update);

    Admin getAdminById(String id);

    R deleteAdmin(String selfId, List<String> ids);

    R getUserList(PageRequest pageRequest, String roleId);

    Admin getAdminByUserId(String id);
}
