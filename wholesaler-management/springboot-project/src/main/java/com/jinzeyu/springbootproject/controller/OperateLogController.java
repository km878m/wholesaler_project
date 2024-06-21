package com.jinzeyu.springbootproject.controller;

import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.request.OperateLogPageRequest;
import com.jinzeyu.springbootproject.service.impl.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/operateLog")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    @GetMapping("/page")
    public Result page(OperateLogPageRequest operateLogPageRequest){
        return Result.success(operateLogService.page(operateLogPageRequest));
    }
}
