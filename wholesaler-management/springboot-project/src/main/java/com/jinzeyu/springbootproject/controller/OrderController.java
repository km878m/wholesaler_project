package com.jinzeyu.springbootproject.controller;

import com.jinzeyu.springbootproject.anno.Log;
import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.request.OrderFormPageRequest;
import com.jinzeyu.springbootproject.pojo.OrderForm;
import com.jinzeyu.springbootproject.pojo.OrderFormItem;
import com.jinzeyu.springbootproject.service.impl.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public Result page(OrderFormPageRequest orderFormPageRequest){
        return Result.success(orderService.page(orderFormPageRequest));
    }

    @PostMapping("/save")
    public Result save(@RequestBody OrderForm orderForm){
        Integer id = orderService.save(orderForm);
        return Result.success(id);
    }

    @Log
    @PostMapping("/update")
    public Result update(@RequestBody OrderForm orderForm){
        orderService.updateById(orderForm);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result byId(@PathVariable Integer id){
        OrderForm orderForm = orderService.byId(id);
        return Result.success(orderForm);
    }

    @Log
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        orderService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/item/save")
    public Result save(@RequestBody OrderFormItem orderFormItem){
        orderService.saveItem(orderFormItem);
        return Result.success();
    }

    @GetMapping("/item/{id}")
    public Result itemById(@PathVariable Integer id){
        OrderFormItem orderFormItem = orderService.itemById(id);
        return Result.success(orderFormItem);
    }

    @DeleteMapping("/item/delete/{id}")
    public Result deleteItem(@PathVariable Integer id){
        orderService.deleteItemById(id);
        return Result.success();
    }

    @PutMapping("/item/update")
    public Result updateItem(@RequestBody OrderFormItem orderFormItem){
        orderService.updateItemById(orderFormItem);
        return Result.success();
    }
}
