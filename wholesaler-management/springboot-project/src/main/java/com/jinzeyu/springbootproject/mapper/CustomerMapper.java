package com.jinzeyu.springbootproject.mapper;

import com.jinzeyu.springbootproject.controller.request.CustomerPageRequest;
import com.jinzeyu.springbootproject.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper {
    //@Select("select * from customer")
    List<Customer> list();

    List<Customer> listByCondition(CustomerPageRequest customerPageRequest);

    void save(Customer customer);

    Customer getById(Integer id);

    void updateById(Customer customer);

    void deleteById(Integer id);
}
