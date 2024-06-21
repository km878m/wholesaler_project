package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.request.SellFormPageRequest;
import com.jinzeyu.springbootproject.pojo.SellForm;
import com.jinzeyu.springbootproject.pojo.SellFormItem;

import java.util.Map;

public interface ISellService {
    Integer save(SellForm sellForm);

    SellForm byId(Integer id);

    void saveItem(SellFormItem sellFormItem);

    void deleteById(Integer id);

    SellFormItem itemById(Integer id);

    void deleteItemById(Integer id);

    void updateItemById(SellFormItem sellFormItem);

    void updateById(SellForm sellForm);

    Object page(SellFormPageRequest sellFormPageRequest);

    Map<String, Object> getCountByTimeRange(String timeRange);
}
