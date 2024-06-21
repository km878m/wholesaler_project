package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.request.OrderFormPageRequest;
import com.jinzeyu.springbootproject.controller.request.SellFormPageRequest;
import com.jinzeyu.springbootproject.pojo.OrderForm;
import com.jinzeyu.springbootproject.pojo.OrderFormItem;
import com.jinzeyu.springbootproject.pojo.SellForm;
import com.jinzeyu.springbootproject.pojo.SellFormItem;

public interface IOrderService {
    Integer save(OrderForm orderForm);

    OrderForm byId(Integer id);

    void saveItem(OrderFormItem orderFormItem);

    void deleteById(Integer id);

    OrderFormItem itemById(Integer id);

    void deleteItemById(Integer id);

    void updateItemById(OrderFormItem orderFormItem);

    void updateById(OrderForm orderForm);

    Object page(OrderFormPageRequest orderFormPageRequest);
}
