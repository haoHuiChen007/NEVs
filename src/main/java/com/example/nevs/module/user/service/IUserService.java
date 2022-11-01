package com.example.nevs.module.user.service;

public interface IUserService {
    //注册
    void reg(String email,String password1,String password2,String code);
    //获取code
    String getCode();
    //发送邮箱
    void sendEmail(String email,String code);
    //修改密码
    void updatePasswordByUid(String uid,String oldPassword,String newPassword1,String newPassword2);
}
