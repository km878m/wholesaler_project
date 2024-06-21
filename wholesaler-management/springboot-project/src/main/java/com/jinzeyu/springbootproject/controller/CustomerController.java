package com.jinzeyu.springbootproject.controller;

import com.jinzeyu.springbootproject.anno.Log;
import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.request.CustomerPageRequest;
import com.jinzeyu.springbootproject.pojo.Customer;
import com.jinzeyu.springbootproject.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Log
    @PostMapping("/save")
    public Result save(@RequestBody Customer customer){
        customerService.save(customer);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Customer> customers = customerService.list();
        return Result.success(customers);
    }

    @GetMapping("/page")
    public Result page(CustomerPageRequest customerPageRequest){
        return Result.success(customerService.page(customerPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Customer customer = customerService.getById(id);
        return Result.success(customer);
    }

    @Log
    @PutMapping("/update")
    public Result update(@RequestBody Customer customer){
        customerService.update(customer);
        return Result.success();
    }

    @Log
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        customerService.deleteById(id);
        return Result.success();
    }
}
