package com.example.nevs.module.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.nevs.common.E;
import com.example.nevs.common.R;
import com.example.nevs.module.user.entity.Admin;
import com.example.nevs.module.user.entity.User;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.util.MD5Util;
import com.example.nevs.util.SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public R addAdmin(Map<String, String> admin) {
        User user= new User();
        Admin insertAdmin = new Admin();
        String salt = SaltUtil.getSalt(8);
        String username = admin.get("username");
        String password = admin.get("password");
        String email = admin.get("email");
        String realName = admin.get("raleName");
        String roleId = admin.get("roleId");
        if (BeanUtil.isNotEmpty(getUserByUsername(username))){
            return R.error(E.USERNAME_REPEAT);
        }
        user.setPassword(MD5Util.inputPassToFromPass(password,salt)).
                setUsername(username)
                .setEmail(email).setRealName(realName)
                .setSalt(salt)
                .setIsDelete(0);
        insertAdmin.setUser(user).setRoleId(roleId);
        Admin insert = mongoTemplate.insert(insertAdmin);
        if (BeanUtil.isNotEmpty(insert)){
            return R.success();
        }
        return R.error(E.INSERT_ERROR);
    }

    @Override
    public R loginAdmin(Map<String, String> login) {
        String username = login.get("username");
        String password = login.get("password");
        User user = getUserByUsername(username).getUser();
        if(user==null){
            return R.error(E.LOGIN_USERNAME_ERROR);
        }
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())){
            return R.error(E.LOGIN_PASSWORD_ERROR);
        }
        String token=JwtUtil.getToken(payload);
        JwtToken jwtToken=new JwtToken(token);

        return null;
    }
    public Admin getUserByUsername(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("user.username").is(username));
        return mongoTemplate.findOne(query, Admin.class);
    }
}
