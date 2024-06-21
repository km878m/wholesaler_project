package com.jinzeyu.springbootproject.controller.request;

import lombok.Data;

@Data
public class OperateLogPageRequest extends BaseRequest{
    private Integer operateUser;
}
