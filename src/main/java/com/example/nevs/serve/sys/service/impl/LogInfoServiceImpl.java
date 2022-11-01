package com.example.nevs.serve.sys.service.impl;

import com.example.nevs.serve.sys.entity.LogInfo;
import com.example.nevs.serve.sys.service.ILogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogInfoServiceImpl implements ILogInfoService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void save(LogInfo logInfo) {
        mongoTemplate.insert(logInfo);
    }
}
