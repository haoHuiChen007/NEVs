package com.example.nevs.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JwtUtil {
    private static final String SING="!Q2W3E4Rafa";

    //生成token
    public static String getToken(Map<String, String> map){
        Calendar instance=Calendar.getInstance();
        instance.add(Calendar.DATE,7);//默认7天过期
        JWTCreator.Builder builder=JWT.create();
        //payload
        map.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SING));
    }
    //验证token,获取token信息
    public static DecodedJWT verify(String token){
        return  JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }
}
