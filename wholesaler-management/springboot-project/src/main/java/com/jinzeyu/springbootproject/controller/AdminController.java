package com.jinzeyu.springbootproject.controller;

import com.jinzeyu.springbootproject.anno.Log;
import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.dto.LoginDTO;
import com.jinzeyu.springbootproject.controller.request.AdminPageRequest;
import com.jinzeyu.springbootproject.controller.request.JobRequest;
import com.jinzeyu.springbootproject.controller.request.LoginRequest;
import com.jinzeyu.springbootproject.controller.request.PasswordRequest;
import com.jinzeyu.springbootproject.pojo.Admin;
import com.jinzeyu.springbootproject.service.impl.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest){
        LoginDTO loginDTO = adminService.login(loginRequest);
        return Result.success(loginDTO);
    }

    @Log
    @PutMapping("/password")
    public Result password(@RequestBody PasswordRequest passwordRequest){
        adminService.changePassword(passwordRequest);
        return Result.success();
    }

    @Log
    @PutMapping("/job")
    public Result job(@RequestBody JobRequest jobRequest){
        adminService.changeJob(jobRequest);
        return Result.success();
    }

    @Log
    @PostMapping("/save")
    public Result save(@RequestBody Admin admin){
        adminService.save(admin);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Admin> admins = adminService.list();
        return Result.success(admins);
    }

    @GetMapping("/page")
    public Result page(AdminPageRequest adminPageRequest){
        return Result.success(adminService.page(adminPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    @Log
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){
        adminService.update(admin);
        return Result.success();
    }

    @Log
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }
}
