package com.example.nevs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@ToString
public class PageRequest {
    private Integer page;
    private Integer size;

}
