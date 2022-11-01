package com.example.nevs.module.user.dao;

import com.example.nevs.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Random;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public User getUserByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query,User.class);
    }

    @Override
    public User getByUid(String uid) {
        return mongoTemplate.findById(uid,User.class);
    }

    @Override
    public User insertUser(User user) {
        User save = mongoTemplate.insert(user);
        return save;
    }

    @Override
    public void updatePasswordByUid(String uid, String password, String salt) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(uid));
        Update update = new Update();
        update.set("password",password);
        update.set("salt",salt);
        mongoTemplate.upsert(query,update,User.class);
    }

    @Override
    public String getMD5Password(String password, String salt) {
        for (int i = 0;i<3;i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    @Override
    public String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
