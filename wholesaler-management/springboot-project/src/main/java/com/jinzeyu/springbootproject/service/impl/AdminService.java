package com.jinzeyu.springbootproject.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.dto.LoginDTO;
import com.jinzeyu.springbootproject.controller.request.AdminPageRequest;
import com.jinzeyu.springbootproject.controller.request.JobRequest;
import com.jinzeyu.springbootproject.controller.request.LoginRequest;
import com.jinzeyu.springbootproject.controller.request.PasswordRequest;
import com.jinzeyu.springbootproject.exception.ServiceException;
import com.jinzeyu.springbootproject.mapper.AdminMapper;
import com.jinzeyu.springbootproject.pojo.Admin;
import com.jinzeyu.springbootproject.service.IAdminService;
import com.jinzeyu.springbootproject.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    private static final String DEFAULT_PASS = "123456";
    private static final String PASS_SALT = "jinzeyu";

    //查询所有管理员方法
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    //管理员分页方法
    @Override
    public Object page(AdminPageRequest adminPageRequest) {
        PageHelper.startPage(adminPageRequest.getPageNum(),adminPageRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(adminPageRequest);
        PageInfo<Admin> info = new PageInfo<>(admins);
        return info;
    }

    //创建管理员方法
    @Override
    public void save(Admin admin) {
        if(StrUtil.isBlank(admin.getPassword())){
            admin.setPassword(DEFAULT_PASS);
        }
        admin.setPassword(securePass(admin.getPassword()));//设置md5加密，加盐
        try {
            adminMapper.save(admin);
        }catch (DuplicateKeyException e) {
            log.error("数据插入失败,username:{}",admin.getUsername());
            throw new ServiceException("用户名重复,请重新输入!!!");
        }
    }

    //根据id查询管理员方法
    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    //更新管理员信息方法
    @Override
    public void update(Admin admin) {
        admin.setUpdateTime(new Date());
        adminMapper.updateById(admin);
    }

    //根据id删除管理员方法
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    //管理员登录方法
    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        loginRequest.setPassword(securePass(loginRequest.getPassword()));//加密密码与数据库密码一致
        Admin admin =  adminMapper.getByUsernameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
        if(admin == null){
            throw new ServiceException("用户名或密码错误");
        }

        if(!admin.isStatus()){
            throw new ServiceException("当前用户处于禁用状态，请联系系统管理员");
        }

        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin,loginDTO);

        //生成token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()),admin.getPassword());

        loginDTO.setToken(token);
        return loginDTO;
    }

    //修改管理员密码方法
    @Override
    public void changePassword(PasswordRequest passwordRequest) {
        //注意 要对新的密码进行加密
        passwordRequest.setNewPass(securePass(passwordRequest.getNewPass()));
        int count = adminMapper.updatePassword(passwordRequest);
        if(count <= 0){
            throw new ServiceException("修改密码失败");
        }
    }

    //修改管理员权限方法
    @Override
    public void changeJob(JobRequest jobRequest) {
        adminMapper.updateJob(jobRequest);
    }

    //加密方法
    private String securePass(String password){
        return SecureUtil.md5(password + PASS_SALT);
    }
}
