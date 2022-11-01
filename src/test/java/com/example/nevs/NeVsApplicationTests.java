package com.example.nevs;

import cn.hutool.core.collection.CollUtil;
import com.example.nevs.common.R;
import com.example.nevs.exception.ex.ServiceException;
import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.car.entity.Params;
import com.example.nevs.module.car.service.ICarService;
import com.example.nevs.module.collection.service.ICollectionService;
import com.example.nevs.module.comment.service.ICommentService;
import com.example.nevs.module.user.entity.Role;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.module.user.service.IRoleService;
import com.example.nevs.module.user.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class NeVsApplicationTests {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ICollectionService collectionService;
    @Autowired
    private ICarService carService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {
        Map<String, String> admin = new HashMap<>();
        admin.put("username", "Admin");
        admin.put("password", "Admin");
        admin.put("realName", "陈浩辉");
        admin.put("email", "2954390791@aa.com");
        admin.put("id", "6350ecf45f5a514f4891cf47");
        System.out.println(adminService.addAdmin(admin));
    }

    @Test
    void testLogin() {
        Map<String, String> login = new HashMap<>();
        login.put("username", "Admin");
        login.put("password", "Admin");
        System.out.println(adminService.loginAdmin(login));
    }

    @Test
    void createRole() {
        Role role = new Role();
        role.setRoleName("管理员");
//        roleService.createRole(role);
    }

    @Test
    void createCollectionInfo() {
        Map<String, String> infor = new HashMap<>();
        infor.put("collectionName", "学习资料");
        infor.put("userId", "635533b2bed4537836b94470");
        System.out.println(collectionService.createCollection(infor));
    }

    @Test
    void createColl() {
        Map<String,String> map = new HashMap<>();
        map.put("phone","1234567");
        map.put("id","635a4987fd4c86111e5e7ce5");
        adminService.updateSelf(map);

    }
    @Test
    void creatColl() {
        List<String> colors = new ArrayList<>(CollUtil.newArrayList("黑色", "红色", "绿色", "蓝色", "橙色","紫红色"));
        List<String> carTypes = new ArrayList<>(CollUtil.newArrayList("乘用车","客车","货车","牵引汽车"));
        List<String> brands = new ArrayList<>(CollUtil.newArrayList("凯迪拉克","宝马","保时捷","奥迪"));
        Params params =new Params();
        params.setColors(colors).setBrands(brands).setCarTypes(carTypes);
        carService.insertParams(params);

    }

    @Test
    void creatColle() {
       Map<String,String> map= new HashMap<>();
       map.put("page","0");
       map.put("size","10");
        R carList = adminService.getCarList(map);
        System.out.println(carList);
    }
    @Test
    void insertImageCar(){
        for (int i = 0; i < 100; i++) {
            Car car = new Car();
            car.setCarName("宝马X"+ i).setCarType("乘用车").setBrand("宝马");
            adminService.insertCar(car);
        }
    }
    @Test
    public void insertComment(){

//        commentService.insertComment()

//        commentService.getCommentList()
    }
    @Test
    public void reg() {
        try {
            userService.reg("2954390791@qq.com","123","123","272385");
            System.out.println("ok");
        } catch (ServiceException e) {
//获取类的对象再获取类的名称
            System.out.println(e.getClass().getSimpleName());
//获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void insertCar(){
        Car car = new Car();
//        car.setCarName("特斯拉")
//        adminService.insertCar()

    }
}
