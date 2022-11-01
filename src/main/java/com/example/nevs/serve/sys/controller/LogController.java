package com.example.nevs.serve.sys.controller;

import com.example.nevs.common.PageRequest;
import com.example.nevs.common.R;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.serve.sys.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private ILogService logService;
    @GetMapping("/getLogInfoById")
    public R getLogInfoById(HttpServletRequest request){
        Map<String, String> map = adminService.jwtPayload(request);
    return logService.getLogInfoById(map.get("id"));
    }

    @GetMapping("/getLogInfo")
    public R getLogInfo(@RequestParam Integer page,@RequestParam Integer size){
        PageRequest pageRequest =new PageRequest(page,size);
        return logService.getLogInfo(pageRequest);
    }
}
