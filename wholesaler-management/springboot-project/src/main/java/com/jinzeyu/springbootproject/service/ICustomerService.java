package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.request.CustomerPageRequest;
import com.jinzeyu.springbootproject.pojo.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICustomerService {
    List<Customer> list();

    Object page(CustomerPageRequest customerPageRequest);

    void save(Customer customer);

    Customer getById(Integer id);

    void update(Customer customer);

    void deleteById(Integer id);
}
