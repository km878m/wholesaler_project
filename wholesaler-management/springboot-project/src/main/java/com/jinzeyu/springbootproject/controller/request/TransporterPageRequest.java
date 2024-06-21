package com.jinzeyu.springbootproject.controller.request;

import lombok.Data;

@Data
public class TransporterPageRequest extends BaseRequest{
    private String name;
    private String phone;
}
