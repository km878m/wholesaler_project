package com.jinzeyu.springbootproject.controller.request;

import lombok.Data;

@Data
public class AdminPageRequest extends BaseRequest{
    private String name;
    private String username;
    private String phone;
}
