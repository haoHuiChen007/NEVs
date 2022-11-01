package com.example.nevs.common;

import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Accessors
public abstract class ValuesCollection {

    private static final Map<Integer, String> type;
    private static final Map<Integer, String> employeeStatus;
    private static final Map<Integer, String> isDelete;
    private static final Map<Integer,String> permission;
    private static final Map<String,String> role;

    static {
        type = new HashMap<>();
        employeeStatus = new HashMap<>();
        isDelete = new HashMap<>();
        permission=new HashMap<>();
        role=new HashMap<>();

        type.put(1, "部门");
        type.put(2, "角色");
        type.put(3, "权限");

        employeeStatus.put(1, "在职");
        employeeStatus.put(2, "休假");
        employeeStatus.put(3, "离职");

        isDelete.put(0,"正常");
        isDelete.put(1,"删除");

        role.put("6350ecf45f5a514f4891cf47","系统管理员");
        role.put("6350ed04e358a1745d802579","管理员");
        role.put(null,"普通用户");

        permission.put(10,"user:*:*");
        permission.put(11,"user:insert:*");
        permission.put(12,"user:delete:*");
        permission.put(13,"user:update:*");
        permission.put(14,"user:find:*");
        permission.put(20,"department:*:*");
        permission.put(21,"department:insert:*");
        permission.put(22,"department:delete:*");
        permission.put(23,"department:update:*");
        permission.put(24,"department:find:*");
        permission.put(30,"admin:*:*");
        permission.put(31,"admin:insert:*");
        permission.put(32,"admin:delete:*");
        permission.put(33,"admin:update:*");
        permission.put(34,"admin:find:*");
        permission.put(40,"employee:*:*");
        permission.put(50,"info:*:*");
    }

    public static Map<String, String> getRole() {
        return role;
    }
}
