package com.example.nevs.serve.sys.service.impl;

import com.example.nevs.common.PageRequest;
import com.example.nevs.common.PageResult;
import com.example.nevs.common.R;
import com.example.nevs.serve.sys.entity.LogInfo;
import com.example.nevs.serve.sys.service.ILogService;
import com.example.nevs.util.IPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IPageHelper pageHelper;

    @Override
    public void setLog(Map<String, String> map) {
        map.get("username");
//        map.get("id")
    }

    @Override
    public R getLogInfoById(String id) {
        Query query =new Query();
        query.addCriteria(Criteria.where("userId").is(id));
        PageResult<LogInfo> logInfoPageResult = pageHelper.pageQuery(query, LogInfo.class, 7, 0);
        return R.success(logInfoPageResult);
    }

    @Override
    public R getLogInfo(PageRequest pageRequest) {
        Query query =new Query();
        PageResult<LogInfo> logInfoPageResult = pageHelper.pageQuery(query, LogInfo.class, pageRequest.getSize(), pageRequest.getPage());
        return R.success(logInfoPageResult);
    }
}
