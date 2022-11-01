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
@Document(value = "co_log_info")
public class LogInfo {

    // 主键id
    private String id;

    // 功能模块
    private String module;

    // 操作类型
    private String type;

    // 操作描述
    private String message;

    // 请求参数
    private String reqParam;

    // 响应参数
    private String resParam;

    // 耗时
    private Long takeUpTime;

    // 操作用户id
    private String userId;

    // 操作用户名称
    private String userName;

    // 操作方法
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

