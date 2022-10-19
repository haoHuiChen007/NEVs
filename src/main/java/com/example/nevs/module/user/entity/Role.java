package com.example.nevs.module.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Role {
    private String id;
    private String roleName;
    private List<Integer> permissions;
}
