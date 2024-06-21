package com.jinzeyu.springbootproject.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SellFormPageRequest extends BaseRequest{
    private String adminName;
    private String customerName;
    private String logisticsName;
    private String status;
    //private LocalDate createTime;
}
