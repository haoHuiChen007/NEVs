package com.example.nevs.config;

import com.example.nevs.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@EnableWebMvc
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/register","/**/excel","/**/import","/user/loginAdmin","/users/reg","/users/sendEmail");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Content-Type","X-Origin","X-Requested-With","accept,Origin","Access-Control-Request-Method","Access-Control-Request-Headers","token","access-control-allow-origin","access-control-allow-methods")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }

}
