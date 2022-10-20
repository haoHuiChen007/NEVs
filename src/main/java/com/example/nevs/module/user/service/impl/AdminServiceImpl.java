package com.example.nevs.module.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.nevs.common.E;
import com.example.nevs.common.PageRequest;
import com.example.nevs.common.R;
import com.example.nevs.module.user.entity.Admin;
import com.example.nevs.module.user.entity.Role;
import com.example.nevs.module.user.entity.User;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.module.user.service.IRoleService;
import com.example.nevs.util.IPageHelper;
import com.example.nevs.util.JwtUtil;
import com.example.nevs.util.MD5Util;
import com.example.nevs.util.SaltUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IPageHelper pageHelper;
    @Autowired
    private IRoleService roleService;

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
                .setLevel(1)
                .setIsDelete(0);
        User u = mongoTemplate.insert(user);
        insertAdmin.setUser(u).setRoleId(roleId);
        Admin insert = mongoTemplate.insert(insertAdmin);
        if (BeanUtil.isNotEmpty(insert)){
            return R.success();
        }
        return R.error(E.INSERT_ERROR);
    }

    @Override
    public R loginAdmin(Map<String, String> login) {
        Map<String,String> payload=new HashMap<>();
        String username = login.get("username");
        String password = login.get("password");
        User user = getUserByUsername(username);
        if(user==null){
            return R.error(E.LOGIN_USERNAME_ERROR);
        }
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())){
            return R.error(E.LOGIN_PASSWORD_ERROR);
        }
        Admin admin = getAdminByUserId(user.getId());
        String token= JwtUtil.getToken(payload);
        payload.put("id",admin.getId());
        payload.put("username",user.getUsername());
        payload.put("email",user.getEmail());
        payload.put("phone",user.getPhone());
        payload.put("roleId",admin.getRoleId());
        return R.success(token);
    }

    @Override
    public R updateSelf(Map<String, String> update) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(update.get("id")));
        Update updateSet = pageHelper.getUpdateSet(update);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, updateSet, Admin.class);
        if(updateResult.wasAcknowledged()){
            return R.success();
        }
        return R.error(E.UPDATE_ERROR);
    }

    @Override
    public Admin getAdminById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Admin.class);
    }

    @Override
    public R deleteAdmin(String selfId, List<String> ids) {
        Admin admin = getAdminById(selfId);
        Role role = roleService.getRoleNameById(admin.getRoleId());
        if (!Objects.equals(role.getRoleName(), "系统管理员")){
            return R.error(E.PERMISSION_ERROR);
        }
        Query query = new Query();
        Update update = Update.update("user.isDelete", 1);
        query.addCriteria(Criteria.where("_id").in(ids));
        mongoTemplate.updateMulti(query,update,Admin.class);
        return R.success();
    }

    @Override
    public R getUserList(PageRequest pageRequest, String roleId) {
        Role role = roleService.getRoleNameById(roleId);
        if (BeanUtil.isNotEmpty(role) && Objects.equals(role.getRoleName(), "系统管理员")){
            Query query = new Query();
            query.addCriteria(Criteria.where("user.isDelete").is(0));
            List<Admin> adminList = mongoTemplate.find(query,Admin.class);
            return R.success(adminList);
        }
        else if (BeanUtil.isNotEmpty(role) && Objects.equals(role.getRoleName(), "管理员")){
            Query query = new Query();
            query.addCriteria(Criteria.where("roleId").is(null).and("user.isDelete").is(0));
            List<Admin> adminList = mongoTemplate.find(query, Admin.class);
            return R.success(adminList);
        }else return null;
    }

    @Override
    public Admin getAdminByUserId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user.id").is(id));
        return mongoTemplate.findOne(query,Admin.class);
    }

    public User getUserByUsername(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class);
    }
}
