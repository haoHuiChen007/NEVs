package com.example.nevs.module.user.dao;


import com.example.nevs.module.user.entity.User;

public interface UserDao {
    //根据email查询user
    User getUserByEmail(String Email);
    //通过uid查找
    User getByUid(String uid);
    //插入user对象
    User insertUser(User user);
    //通过id改密码
    void updatePasswordByUid(String uid,String password,String salt);


    //md5算法的加密处理
    String getMD5Password(String password, String salt);

    //随机名字生成
    String getStringRandom(int length);
}
