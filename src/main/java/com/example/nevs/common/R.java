package com.example.nevs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    private long code;
    private String message;
    private Object data;
    public static R success(){
        return new R(E.SUCCESS.getCode(), E.SUCCESS.getMessage(),null);
    }

    public static R success(Object data){
        return new R(E.SUCCESS.getCode(),R.success().getMessage(),data);
    }
    public static R error(E responseBeanEnum){
        return new R(responseBeanEnum.getCode(), responseBeanEnum.getMessage(),null);
    }

    public static R error(E responseBeanEnum, Object data){
        return new R(responseBeanEnum.getCode(), responseBeanEnum.getMessage(),data);
    }

}
