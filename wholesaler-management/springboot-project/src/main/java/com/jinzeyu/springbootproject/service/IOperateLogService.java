package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.request.OperateLogPageRequest;

public interface IOperateLogService {
    Object page(OperateLogPageRequest operateLogPageRequest);
}
