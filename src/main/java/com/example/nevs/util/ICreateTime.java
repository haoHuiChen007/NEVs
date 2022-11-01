package com.example.nevs.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ICreateTime {//精确到秒级
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date  getIdTime(String id) {
        //        return sdf.format(date);
        return new Date(Long.parseLong(Integer.parseInt(id.substring(0, 8), 16) + "000"));
    }
}
