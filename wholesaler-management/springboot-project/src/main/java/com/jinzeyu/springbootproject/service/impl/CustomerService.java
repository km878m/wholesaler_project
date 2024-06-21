package com.jinzeyu.springbootproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.request.CustomerPageRequest;
import com.jinzeyu.springbootproject.mapper.CustomerMapper;
import com.jinzeyu.springbootproject.pojo.Customer;
import com.jinzeyu.springbootproject.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    // 查询所有客户方法
    @Override
    public List<Customer> list() {
        return customerMapper.list();
    }

    // 客户分页方法
    @Override
    public Object page(CustomerPageRequest customerPageRequest) {
        PageHelper.startPage(customerPageRequest.getPageNum(),customerPageRequest.getPageSize());
        List<Customer> customers = customerMapper.listByCondition(customerPageRequest);
        PageInfo<Customer> info = new PageInfo<>(customers);
        //Page<Customer> p = (Page<Customer>) customers;
        return info;
    }

    // 添加客户方法
    @Override
    public void save(Customer customer) {
        customerMapper.save(customer);
    }

    // 根据id查询客户方法(用于数据回显)
    @Override
    public Customer getById(Integer id) {
        return customerMapper.getById(id);
    }

    //更新客户信息方法
    @Override
    public void update(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateById(customer);
    }

    // 根据id删除客户方法
    @Override
    public void deleteById(Integer id) {
        customerMapper.deleteById(id);
    }
}
