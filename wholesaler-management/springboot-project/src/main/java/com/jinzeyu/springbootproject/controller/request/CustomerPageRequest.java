package com.jinzeyu.springbootproject.controller.request;

import lombok.Data;

@Data
public class CustomerPageRequest extends BaseRequest{
    private String name;
    private String phone;
}
