package com.example.nevs.module.user.controller;

import com.example.nevs.common.R;
import com.example.nevs.module.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController  {

    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
//   登入

    //发送邮箱验证
    @GetMapping("/sendEmail")
    public R sendEmailCode(@RequestParam String email){
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)){
            return  R.success();
        }
        code = userService.getCode();
        userService.sendEmail(email,code);
        assert code != null;
        redisTemplate.opsForValue().set(email,code,1,TimeUnit.MINUTES);
        return  R.success();
    }

    //注册
    @PostMapping("/reg")
    public R reg(@RequestBody Map<String,String> map) {
        String email = map.get("email");
        String password1 = map.get("password1");
        String password2 = map.get("password2");
        String code = map.get("code");
        userService.reg(email,password1,password2,code);
        return  R.success();
    }

    //修改用户密码
    @RequestMapping("change_password")
    public R changePassword(String uid,String oldPassword, String newPassword1, String newPassword2){
        userService.updatePasswordByUid(uid,oldPassword,newPassword1,newPassword2);
        return R.success();
    }
}
