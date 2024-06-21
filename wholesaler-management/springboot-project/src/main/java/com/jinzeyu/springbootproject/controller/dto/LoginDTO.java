package com.jinzeyu.springbootproject.controller.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;
    private String name;
    private String username;
    private String sex;
    private String phone;
    private String address;
    private String email;
    private String job;
    private String token;
}
