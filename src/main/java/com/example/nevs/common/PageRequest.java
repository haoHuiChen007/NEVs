package com.example.nevs.common;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class PageRequest {
    private Integer page;
    private Integer size;

}
