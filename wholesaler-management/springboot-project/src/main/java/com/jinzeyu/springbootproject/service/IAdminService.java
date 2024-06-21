package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.dto.LoginDTO;
import com.jinzeyu.springbootproject.controller.request.AdminPageRequest;
import com.jinzeyu.springbootproject.controller.request.JobRequest;
import com.jinzeyu.springbootproject.controller.request.LoginRequest;
import com.jinzeyu.springbootproject.controller.request.PasswordRequest;
import com.jinzeyu.springbootproject.pojo.Admin;

import java.util.List;

public interface IAdminService {
    List<Admin> list();

    Object page(AdminPageRequest adminPageRequest);

    void save(Admin admin);

    Admin getById(Integer id);

    void update(Admin admin);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest loginRequest);

    void changePassword(PasswordRequest passwordRequest);

    void changeJob(JobRequest jobRequest);
}
