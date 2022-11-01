package com.example.nevs.module.user.service.impl;

import com.example.nevs.exception.EmailSendException;
import com.example.nevs.exception.ex.*;
import com.example.nevs.module.user.dao.UserDao;
import com.example.nevs.module.user.entity.User;
import com.example.nevs.module.user.service.IUserService;
import com.example.nevs.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDao userDao;


    /**
     * 注册
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void reg(String email, String password1, String password2, String code) {
        User result = userDao.getUserByEmail(email);
        User user = new User();
        if (result != null) {
            throw new UsernameDuplicateException("邮箱被占用");
        }
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
            throw new EmailSendException("邮箱验证异常");
        }
        String mobleCode = redisTemplate.opsForValue().get(email);
        System.out.println(mobleCode);
        if (!code.equals(mobleCode)) {
            throw new CodeNotMatchException("验证码匹配异常");
        }
        if (!password1.equals(password2)) {
            throw new PasswordNotMatchException("两次输入密码不一致");
        }
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        user.setUsername(email);
        user.setPassword(MD5Util.inputPassToFromPass(password1,salt));
        user.setEmail(email);
        System.out.println(user);
        User save = userDao.insertUser(user);

        if (save == null) {
            throw new InsertException("在用户注册过程中产生了未知异常");
        }
    }

    @Override
    public String getCode() {
        int random = (int) (Math.random() * 1000000);
        return String.format("%06d", random);
    }


    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String UserName;
    @Override
    @Async
    public void sendEmail(String email, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("卡尔新能源登录验证码:");
        mailMessage.setText("尊敬的" + email + "\n您注册的验证码为:" + code + "有效时间1分钟");
        mailMessage.setTo(email);
        mailMessage.setFrom(UserName);
        // 真正的发送邮件操作，从 from到 to
        mailSender.send(mailMessage);
    }




    @Override
    public void updatePasswordByUid(String uid, String oldPassword, String newPassword1, String newPassword2) {
        User result = userDao.getByUid(uid);
        if (result == null) {
            throw new UserNotFoundException("用户查询出错");
        }
        String oldSalt = result.getSalt();
        String tureOldPassword = userDao.getMD5Password(oldPassword,oldSalt);
        if (!tureOldPassword.equals(result.getPassword())) {
            throw new PasswordNotMatchException("密码输入错误");
        }
        if (!newPassword1.equals(newPassword2)) {
            throw new PasswordNotMatchException("两次密码不匹配");
        }
        String salt = UUID.randomUUID().toString().toUpperCase();
        String password = userDao.getMD5Password(newPassword1, salt);
        userDao.updatePasswordByUid(uid, password, salt);
    }
}
