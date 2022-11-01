package com.example.nevs.serve.sys.entity;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作模块
     */
    String module() default "";

    /**
     * 操作类型
     */
    String type() default "";

    /**
     * 操作说明
     */
    String desc() default "";

}

