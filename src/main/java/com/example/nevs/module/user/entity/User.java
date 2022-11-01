package com.example.nevs.module.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("co_user")
@Data
@Accessors(chain = true)


public  class User {
    @Value("${ip}")
    private String ip;
    private String id;
    private String username;
    private String password;
    private String realName;
    private String head = "http://192.168.135.198/nevs/user/20200922022838-5f6961562471f.jpg";
    private String email;
    private String phone;
    private String salt;
    private Integer level;
    private String roleId;
}
