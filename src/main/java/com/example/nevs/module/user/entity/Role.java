package com.example.nevs.module.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Accessors(chain = true)
@Document("co_role")
public class Role {
    private String id;
    private String roleName;
}
