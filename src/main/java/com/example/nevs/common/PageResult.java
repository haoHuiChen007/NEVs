package com.example.nevs.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageResult <T>  {
    private Integer page;
    private Integer size;
    private Integer totalElements;
    private Integer totalPages;
    private List<T> content;


}
