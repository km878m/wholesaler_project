package com.jinzeyu.springbootproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.request.OrderFormPageRequest;
import com.jinzeyu.springbootproject.mapper.OrderMapper;
import com.jinzeyu.springbootproject.mapper.ProductMapper;
import com.jinzeyu.springbootproject.pojo.OrderForm;
import com.jinzeyu.springbootproject.pojo.OrderFormItem;
import com.jinzeyu.springbootproject.service.IOrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    // 采购单创建方法
    @Override
    public Integer save(OrderForm orderForm) {
        //时间:精确到毫秒
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String localDate = LocalDateTime.now().format(ofPattern);
        //3位随机数
        String randomNumeric = RandomStringUtils.randomNumeric(3);
        String orderNumber = localDate + randomNumeric;
        orderForm.setOrderNumber(orderNumber);

        orderMapper.save(orderForm);
        Integer id = orderMapper.returnId(orderNumber);
        return id;
    }

    // 根据id查询采购单（用于数据回显）
    @Override
    public OrderForm byId(Integer id) {
        OrderForm orderForm = orderMapper.byId(id);
        return orderForm;
    }

    // 采购单生成方法
    @Override
    public void saveItem(OrderFormItem orderFormItem) {
        //计算单项总价格
        Integer price = orderFormItem.getPrice();
        Integer totalPrices = price*orderFormItem.getNumber();
        orderFormItem.setTotalPrices(totalPrices);

        //插入产品规格
        String description = productMapper.getDescriptionByName(orderFormItem.getProductName());
        orderFormItem.setProductDescription(description);

        //增加采购单总件数
        Integer number = orderMapper.selectNumberById(orderFormItem.getPid());
        orderMapper.updateNumberById(orderFormItem.getPid(),number + orderFormItem.getNumber());

        //增加采购单总价
        Integer formTotalPrices = orderMapper.selectTotalPricesById(orderFormItem.getPid());
        orderMapper.updateTotalPricesById(orderFormItem.getPid(),formTotalPrices + totalPrices);

        orderMapper.saveItem(orderFormItem);
    }

    // 根据id删除采购单
    @Override
    public void deleteById(Integer id) {
        orderMapper.deleteById(id);
        orderMapper.deleteByPid(id);
    }

    // 根据id查询采购单单项（用于数据回显）
    @Override
    public OrderFormItem itemById(Integer id) {
        return orderMapper.itemById(id);
    }

    // 根据id删除采购单单项
    @Override
    public void deleteItemById(Integer id) {
        OrderFormItem orderFormItem = orderMapper.itemById(id);
        //减少采购单总件数
        Integer number = orderMapper.selectNumberById(orderFormItem.getPid());
        orderMapper.updateNumberById(orderFormItem.getPid(),number - orderFormItem.getNumber());

        //减少采购单总价
        Integer formTotalPrices = orderMapper.selectTotalPricesById(orderFormItem.getPid());
        orderMapper.updateTotalPricesById(orderFormItem.getPid(),formTotalPrices - orderFormItem.getTotalPrices());

        orderMapper.deleteItemById(id);
    }

    // 添加采购单单项
    @Override
    public void updateItemById(OrderFormItem orderFormItem) {
        Integer totalPrices = orderFormItem.getPrice()*orderFormItem.getNumber();
        orderFormItem.setTotalPrices(totalPrices);
        orderMapper.updateItemById(orderFormItem);
    }

    // 采购单确认入库方法
    @Override
    public void updateById(OrderForm orderForm) {
        orderForm.setUpdateTime(LocalDate.now());

        //增加库存
        Iterator<OrderFormItem> iter =orderForm.getChildren().iterator();
        while(iter.hasNext()){
            OrderFormItem orderFormItem = iter.next();
            String productName = orderFormItem.getProductName();
            Integer number = productMapper.getNumberByName(productName);
            productMapper.updateNumberByName(productName,number + orderFormItem.getNumber());
        }

        orderMapper.updateById(orderForm);
    }

    //采购单分页方法
    @Override
    public Object page(OrderFormPageRequest orderFormPageRequest) {
        PageHelper.startPage(orderFormPageRequest.getPageNum(),orderFormPageRequest.getPageSize());
        List<OrderForm> orderForms = orderMapper.listByCondition(orderFormPageRequest);
        PageInfo<OrderForm> info = new PageInfo<>(orderForms);
        return info;
    }
}
