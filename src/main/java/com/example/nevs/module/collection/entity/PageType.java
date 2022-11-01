package com.example.nevs.module.collection.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageType<E> {
    private Integer pageSize;
    private Integer total;
    private Integer currentPage;
    private Integer pageCount;//总页数
    private List<E> rows;
}
