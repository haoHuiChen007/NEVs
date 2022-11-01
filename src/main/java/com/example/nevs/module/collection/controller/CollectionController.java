package com.example.nevs.module.collection.controller;

import com.example.nevs.common.R;
import com.example.nevs.module.car.entity.Car;
import com.example.nevs.module.collection.entity.CollectionInfo;
import com.example.nevs.module.collection.entity.PageType;
import com.example.nevs.module.collection.service.ICollectionService;
import com.example.nevs.module.user.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection")
@CrossOrigin
public class CollectionController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private ICollectionService collectionService;
    @PostMapping("/deleteByCarId")
    public R deleteByCarId(@RequestParam Map<String,String> map, HttpServletRequest request){
        Map<String, String> jwtPayload = adminService.jwtPayload(request);
        String userId = jwtPayload.get("id");
        map.put("userId",userId);
        return collectionService.deleteByCarId(map);
    }

    @PostMapping("/createCollection")
    public R createCollection(@RequestParam Map<String,String> map, HttpServletRequest request){
        Map<String, String> jwtPayload = adminService.jwtPayload(request);
        String userId = jwtPayload.get("id");
        map.put("userId",userId);
        return collectionService.createCollection(map);
    }

    private static final int ok = 200;

    //收藏小窗口
    @RequestMapping("/smallWin")
    public R smallWin(@RequestParam String userId){
        List<CollectionInfo> infoList = collectionService.selectCollection(userId);
        return R.success(infoList);
    }
    //创建收藏夹
    @RequestMapping("/creatCollection")
    public R insertCollection(@RequestParam String userId,@RequestParam String collectionName){
        collectionService.insertCollection(userId,collectionName);
        return R.success();
    }
    //删除收藏夹
    @RequestMapping("/deleteCollection")
    public R deleteCollection(@RequestParam String id){
        collectionService.deleteCollection(id);
        return R.success();
    }
    //添加收藏
    @RequestMapping("/addCollect")
    public R insertCarToCollection(@RequestParam String id,@RequestParam String carId){
        collectionService.insertCarToCollection(id,carId);
        return R.success();
    }
    //取消收藏
    @RequestMapping("/cancelCollect")
    public R removeCarFromCollection(@RequestParam String id,@RequestParam String carId){
        collectionService.removeCarFromCollection(id,carId);
        return R.success();
    }
    //展示收藏夹
    @RequestMapping("/displayCollection")
    public R displayCollection(@RequestParam PageType pageType, @RequestParam(" ") String id){
        Integer currentPage = pageType.getCurrentPage();
        Integer pageSize = pageType.getPageSize();
        List<Car> cars = collectionService.displayCollection(currentPage, pageSize, id);
        return R.success(cars);
    }

}
