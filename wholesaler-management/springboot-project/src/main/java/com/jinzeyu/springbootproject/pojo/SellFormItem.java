package com.jinzeyu.springbootproject.pojo;

import lombok.Data;

@Data
public class SellFormItem {
    private Integer id;
    private Integer pid;
    private String productName;
    private Integer price;
    private Integer totalPrices;
    private Integer number;
    private String productDescription;
}
