package com.jinzeyu.springbootproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.request.OperateLogPageRequest;
import com.jinzeyu.springbootproject.mapper.OperateLogMapper;
import com.jinzeyu.springbootproject.pojo.Customer;
import com.jinzeyu.springbootproject.pojo.OperateLog;
import com.jinzeyu.springbootproject.service.IOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogService implements IOperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    // 操作日志分页方法
    @Override
    public Object page(OperateLogPageRequest operateLogPageRequest) {
        PageHelper.startPage(operateLogPageRequest.getPageNum(),operateLogPageRequest.getPageSize());
        List<OperateLog> operateLogs = operateLogMapper.listByCondition(operateLogPageRequest);
        PageInfo<OperateLog> info = new PageInfo<>(operateLogs);
        return info;
    }
}
