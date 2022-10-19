package com.example.nevs.module.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("co_user")
@Data
@Accessors(chain = true)

public  class User {
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private String salt;
    private Integer isDelete = 0;
}
