package com.example.nevs.module.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document("co_admin")
public class Admin {
    private String id;
    @DBRef
    private User user;
    private String roleId;
}
