package com.example.nevs.module.user.service;

import com.example.nevs.common.R;

import java.util.Map;

public interface IAdminService {
    R addAdmin (Map<String,String> admin);

    R loginAdmin(Map<String, String> login);
}
