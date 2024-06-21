package com.jinzeyu.springbootproject.mapper;

import com.jinzeyu.springbootproject.controller.request.*;
import com.jinzeyu.springbootproject.pojo.Admin;
import com.jinzeyu.springbootproject.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<Admin> list();

    List<Admin> listByCondition(AdminPageRequest adminPageRequest);

    void save(Admin admin);

    Admin getById(Integer id);

    void updateById(Admin admin);

    void deleteById(Integer id);

    Admin getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    int updatePassword(PasswordRequest passwordRequest);

    void updateJob(JobRequest jobRequest);
}
