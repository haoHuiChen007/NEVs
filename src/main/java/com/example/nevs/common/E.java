package com.example.nevs.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 枚举类用于返回错误信息以用于复用
 */
@Getter
@ToString
@AllArgsConstructor
public enum E {
    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务器异常"),
    //文件上传异常
    FILE_UPLOAD_FAIL(408,"文件上传失败"),
    DELETE_FILE_FAIL(409,"文件删除失败"),
    //文档添加失败
    INSERT_ERROR(501,"文档插入失败"),
    UPDATE_ERROR(502,"更新失败"),
    DELETE_ERROR(503,"删除异常"),
    //用户登入
    USERNAME_REPEAT(1001, "用户名重复"),
    LOGIN_USERNAME_ERROR(1002,"用户名错误"),
    LOGIN_PASSWORD_ERROR(1003,"密码错误")
    ;
    private final Integer code;
    private final String message;
}
