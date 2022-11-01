package com.example.nevs.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import static com.example.nevs.util.JwtUtil.verify;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if("OPTIONS".equals(request.getMethod()))return true;
        Map<String,Object> map=new HashMap<>();
        String token=request.getHeader("token");
        try{
            verify(token);
            return true;
        }catch (SignatureVerificationException e){
            map.put("msg","无效签名!");
        }catch (TokenExpiredException e){
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException e){
            map.put("msg","Token算法不一致!");
        }catch (Exception e){
            map.put("msg","token无效");
        }
        map.put("state",false);//设置状态
        String json=new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        return false;
    }
}
