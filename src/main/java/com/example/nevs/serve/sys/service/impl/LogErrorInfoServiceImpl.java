package com.example.nevs.serve.sys.service.impl;

import com.example.nevs.serve.sys.entity.LogErrorInfo;
import com.example.nevs.serve.sys.service.ILogErrorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogErrorInfoServiceImpl implements ILogErrorInfoService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(LogErrorInfo logErrorInfo) {
        mongoTemplate.insert(logErrorInfo);
    }
}
