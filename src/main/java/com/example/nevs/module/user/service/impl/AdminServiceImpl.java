package com.example.nevs.module.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.nevs.common.*;
import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.comment.emtity.Comment;
import com.example.nevs.module.user.entity.Role;
import com.example.nevs.module.user.entity.User;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.module.user.service.IRoleService;
import com.example.nevs.serve.sys.entity.LogErrorInfo;
import com.example.nevs.serve.sys.entity.LogInfo;
import com.example.nevs.util.IPageHelper;
import com.example.nevs.util.JwtUtil;
import com.example.nevs.util.MD5Util;
import com.example.nevs.util.SaltUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IPageHelper pageHelper;
    @Autowired
    private IRoleService roleService;

    @Override
    public R addAdmin(Map<String, String> admin) {
        User user= new User();
        String salt = SaltUtil.getSalt(8);
        String username = admin.get("username");
        String password = admin.get("password");
        String email = admin.get("email");
        String realName = admin.get("raleName");
        String roleId = admin.get("id");
        if (BeanUtil.isNotEmpty(getUserByUsername(username)) && StrUtil.isNotBlank(username)){
            return R.error(E.USERNAME_REPEAT);
        }
        user.setPassword(MD5Util.inputPassToFromPass(password,salt))
                .setUsername(username)
                .setEmail(email).setRealName(realName)
                .setSalt(salt)
                .setRoleId(roleId)
                .setLevel(1);
        User u = mongoTemplate.insert(user);
        if (BeanUtil.isNotEmpty(u)){
            return R.success();
        }
        return R.error(E.INSERT_ERROR);
    }

    @Override
    public R loginAdmin(Map<String, String> login) {
        Map<String,String> payload=new HashMap<>();
        String username = login.get("username");
        String password = login.get("password");
        User user = getUserByUsername(username);
        if(user==null){
            return R.error(E.LOGIN_USERNAME_ERROR);
        }
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())){
            return R.error(E.LOGIN_PASSWORD_ERROR);
        }
        payload.put("id",user.getId());
        payload.put("username",user.getUsername());
        payload.put("email",user.getEmail());
        payload.put("phone",user.getPhone());
        payload.put("roleId",user.getRoleId());
        payload.put("head",user.getHead());
        payload.put("realName",user.getRealName());
        String token= JwtUtil.getToken(payload);
        Map<String, Object> data = new HashMap<>();
        data.put("token",token);
        data.put("user",payload);
        return R.success(data);
    }

    @Override
    public R updateSelf(Map<String, String> update) {
        User user =new User();
        BeanUtil.copyProperties(update,user);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(user.getId()));
        Update updateSet = pageHelper.getUpdateSet(user);
        if (StrUtil.isNotBlank(user.getPassword())){
            String salt = SaltUtil.getSalt(8);
            updateSet.set("salt",salt);
            updateSet.set("password",MD5Util.inputPassToFromPass(user.getPassword(),salt));
        }
        UpdateResult updateResult = mongoTemplate.updateFirst(query, updateSet, User.class);
        if(updateResult.wasAcknowledged()){
            return R.success();
        }
        return R.error(E.UPDATE_ERROR);
    }

    @Override
    public User getAdminById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public R deleteAdmin(String selfId,String id) {
        User admin = getAdminById(selfId);
        Role role = roleService.getRoleNameById(admin.getRoleId());
        if (!Objects.equals(role.getRoleName(), "系统管理员")){
            return R.error(E.PERMISSION_ERROR);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        User andRemove = mongoTemplate.findAndRemove(query, User.class);
        if (BeanUtil.isEmpty(andRemove)){
            return R.error(E.DELETE_ERROR);
        }
        return R.success();
    }

    @Override
    public R getUserList(PageRequest pageRequest, String roleId,String username) {
        Role role = roleService.getRoleNameById(roleId);
        Query query = new Query();
        if (username!=null) {
            query.addCriteria(Criteria.where("username").regex("^.*" +username+ ".*$"));
        }
        if (BeanUtil.isNotEmpty(role) && Objects.equals(role.getRoleName(), "系统管理员")){
            PageResult<User> adminPageResult = pageHelper.pageQuery(query, User.class, pageRequest.getSize(), pageRequest.getPage());
            List<User> users = outputUserInfo(adminPageResult);
            adminPageResult.setContent(users);
            return R.success(adminPageResult);
        }
        else if (BeanUtil.isNotEmpty(role) && Objects.equals(role.getRoleName(), "管理员")){
            query.addCriteria(Criteria.where("roleId").is(null));
            PageResult<User> adminPageResult = pageHelper.pageQuery(query, User.class, pageRequest.getSize(), pageRequest.getPage());
            List<User> users = outputUserInfo(adminPageResult);
            adminPageResult.setContent(users);
            return R.success(adminPageResult);
        }else return null;
    }

    private List<User> outputUserInfo(PageResult<User> adminPageResult) {
//        adminPageResult.getContent().stream().filter(user -> user.getRoleId()!="6350ecf45f5a514f4891cf47");
        List<User> users= new ArrayList<>();
        for (User user : adminPageResult.getContent()) {
            User u= new User();
            user.setRoleId(ValuesCollection.getRole().get(user.getRoleId()));
            if (StrUtil.isEmpty(user.getPhone())) {
                user.setPhone("未添加电话");
            }
            if (StrUtil.isEmpty(user.getEmail())) {
                user.setEmail("未添加邮箱");
            }
            BeanUtil.copyProperties(user,u,"password","salt");
            users.add(u);
        }
        return users;
    }

    @Override
    public User getAdminByUserId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,User.class);
    }

    @Override
    public R insertCar(Car car) {
        mongoTemplate.insert(car);
        return R.success();
    }

    @Override
    public Map<String, String> jwtPayload(HttpServletRequest request) {
        Map<String,String> jwtPayload = new HashMap<>();
        String token=request.getHeader("token");
        DecodedJWT decodedJWT = JwtUtil.verify(token);
        String id=decodedJWT.getClaim("id").asString();
        String username=decodedJWT.getClaim("username").asString();
        String email=decodedJWT.getClaim("email").asString();
        String phone=decodedJWT.getClaim("phone").asString();
        String roleId=decodedJWT.getClaim("roleId").asString();
        jwtPayload.put("id",id);
        jwtPayload.put("username",username);
        jwtPayload.put("email",email);
        jwtPayload.put("phone",phone);
        jwtPayload.put("roleId",roleId);
        return jwtPayload;
    }

    @Override
    public R getCarById(String id) {
        return null;
    }

    @Override
    public R getCarList(Map<String,String> map) {
        String carName = map.get("carName");
        String carType = map.get("carType");
        String brand = map.get("brand");
        Query query =new Query();
        if (StrUtil.isNotBlank(carName)){
            query.addCriteria(Criteria.where("carName").regex("^.*" +carName+ ".*$"));
        }if (StrUtil.isNotBlank(carType)){
            query.addCriteria(Criteria.where("carType").is(carType));
        }if (StrUtil.isNotBlank(brand)){
            query.addCriteria(Criteria.where("brand").is(brand));
        }
        PageResult<Car> carPageResult = pageHelper.pageQuery(query, Car.class, Integer.valueOf(map.get("size")), Integer.valueOf(map.get("page")));
        return R.success(carPageResult);
    }

    @Override
    public R updateCarById(Car car) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(car.getId()));
        Update updateSet = pageHelper.getUpdateSet(car);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, updateSet, Car.class);
        if (updateResult.wasAcknowledged()){
            return R.success();
        }
        return R.error(E.UPDATE_ERROR);
    }

    @Override
    public R deleteCarById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Car andRemove = mongoTemplate.findAndRemove(query, Car.class);
        if (BeanUtil.isNotEmpty(andRemove)){
            return R.success();
        }
        return R.error(E.DELETE_ERROR);
    }

    public User getUserByUsername(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public R getNum() {
        Map<String,Long> map = new HashMap<>();
        map.put("userNum",getUserNum());
        map.put("carNum",getCarNum());
        map.put("commentNum",getCommentNum());
        map.put("logNum",getLogNum());
        map.put("logError",getLogErrorNum());
        return R.success(map);
    }
    private long getUserNum(){
        Query query = new Query();
        return mongoTemplate.count(query, User.class);
    }
    private long getCarNum(){
        Query query =  new Query();
        return mongoTemplate.count(query,Car.class);
    }
    private long getCommentNum(){
        Query query = new Query();
        return mongoTemplate.count(query, Comment.class);
    }
    private long getLogNum(){
        Query query = new Query();
        return mongoTemplate.count(query, LogInfo.class);
    }
    private long getLogErrorNum(){
        Query query = new Query();
        return mongoTemplate.count(query, LogErrorInfo.class);
    }

}
