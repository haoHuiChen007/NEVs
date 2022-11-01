package com.example.nevs.serve.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "co_log_error_info")
public class LogErrorInfo {

    // 主键id
    private String id;

    // 请求参数
    private String reqParam;

    // 异常名称
    private String name;

    // 异常信息
    private String message;

    // 操作用户id
    private String userId;

    // 操作用户名称
    private String userName;

    // 请求方法
    private String method;

    // 请求url
    private String uri;

    // 请求IP
    private String ip;

    // 版本号
    private String version;

    // 创建时间
    private LocalDateTime createTime;

}

