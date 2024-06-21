package com.jinzeyu.springbootproject.controller.request;

import lombok.Data;

@Data
public class OrderFormPageRequest extends BaseRequest{
    private String adminName;
    private String status;
    //private LocalDate createTime;
}
