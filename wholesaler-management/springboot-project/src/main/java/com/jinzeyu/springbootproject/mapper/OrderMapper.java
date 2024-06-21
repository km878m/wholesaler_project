package com.jinzeyu.springbootproject.mapper;

import com.jinzeyu.springbootproject.controller.request.OrderFormPageRequest;
import com.jinzeyu.springbootproject.controller.request.SellFormPageRequest;
import com.jinzeyu.springbootproject.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void save(OrderForm orderForm);

    OrderForm byId(Integer id);

    Integer returnId(String orderNumber);

    void saveItem(OrderFormItem orderFormItem);

    void deleteById(Integer id);

    void deleteByPid(Integer id);

    OrderFormItem itemById(Integer id);

    void deleteItemById(Integer id);

    void updateItemById(OrderFormItem orderFormItem);

    void updateById(OrderForm orderForm);

    List<OrderForm> listByCondition(OrderFormPageRequest orderFormPageRequest);

    Integer selectNumberById(Integer id);

    Integer selectTotalPricesById(Integer id);

    void updateNumberById(Integer id, Integer number);

    void updateTotalPricesById(Integer id, Integer totalPrices);

    List<SellReturnCountPO> getPriceCountByTimeRange(String timeRange);
}
