package com.example.nevs.serve.sys.service;

import com.example.nevs.common.PageRequest;
import com.example.nevs.common.R;

import java.util.Map;

public interface ILogService {
    void setLog(Map<String,String> map);

    R getLogInfoById(String id);

    R getLogInfo(PageRequest pageRequest);
}
