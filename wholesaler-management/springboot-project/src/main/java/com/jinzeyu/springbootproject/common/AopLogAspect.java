package com.jinzeyu.springbootproject.common;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.jinzeyu.springbootproject.mapper.OperateLogMapper;
import com.jinzeyu.springbootproject.pojo.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect // 指定当前类为切面类
public class AopLogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.jinzeyu.springbootproject.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //操作人的id - 当前登录管理员的id
        //获取请求头中的jwt令牌，解析令牌得到id
        String token = request.getHeader("token");
        String adminId = JWT.decode(token).getAudience().get(0);
        Integer operateUser = Integer.parseInt(adminId);

        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //操作类的类名
        String className = joinPoint.getTarget().getClass().getName();

        //操作的方法名
        String methodName = joinPoint.getSignature().getName();

        //操作方法传入的参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        //调用原始方法运行
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        //方法执行的返回值
        String returnValue = JSONObject.toJSONString(result);

        //方法操作耗时
        Long costTime = end - begin;


        //记录操作日志
        OperateLog operateLog = new OperateLog(null,operateUser,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);

        log.info("AOP记录操作日志");

        return result;
    }
}
