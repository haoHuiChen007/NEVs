package com.example.nevs.module.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.nevs.common.E;
import com.example.nevs.common.R;
import com.example.nevs.module.user.entity.Role;
import com.example.nevs.module.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public R getRoleList() {
        Query query = new Query();
        List<Role> roles = mongoTemplate.find(query, Role.class);
        return R.success(roles);
    }

    @Override
    public Role getRoleNameById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Role.class);
    }

    @Override
    public R createRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        Role insert = mongoTemplate.insert(role);
        if (BeanUtil.isNotEmpty(insert)){
            return R.success();
        }
        return R.error(E.INSERT_ERROR);
    }

    @Override
    public R deleteRole(String id) {
        return null;
    }
}
