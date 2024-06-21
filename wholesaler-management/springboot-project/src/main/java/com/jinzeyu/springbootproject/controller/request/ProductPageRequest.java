package com.jinzeyu.springbootproject.controller.request;

import lombok.Data;

@Data
public class ProductPageRequest extends BaseRequest{
    private String name;
    private String description;
    private String manufacturer;
    private String category;
}
