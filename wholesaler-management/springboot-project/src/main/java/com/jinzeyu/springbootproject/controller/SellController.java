package com.jinzeyu.springbootproject.controller;

import com.jinzeyu.springbootproject.anno.Log;
import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.request.SellFormPageRequest;
import com.jinzeyu.springbootproject.pojo.SellForm;
import com.jinzeyu.springbootproject.pojo.SellFormItem;
import com.jinzeyu.springbootproject.service.impl.SellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sell")
public class SellController {

    @Autowired
    private SellService sellService;

    @GetMapping("/page")
    public Result page(SellFormPageRequest sellFormPageRequest){
        return Result.success(sellService.page(sellFormPageRequest));
    }

    @PostMapping("/save")
    public Result save(@RequestBody SellForm sellForm){
        Integer id = sellService.save(sellForm);
        return Result.success(id);
    }

    @Log
    @PostMapping("/update")
    public Result update(@RequestBody SellForm sellForm){
        sellService.updateById(sellForm);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result byId(@PathVariable Integer id){
        SellForm sellForm = sellService.byId(id);
        return Result.success(sellForm);
    }

    @Log
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        sellService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/item/save")
    public Result save(@RequestBody SellFormItem sellFormItem){
        sellService.saveItem(sellFormItem);
        return Result.success();
    }

    @GetMapping("/item/{id}")
    public Result itemById(@PathVariable Integer id){
        SellFormItem sellFormItem = sellService.itemById(id);
        return Result.success(sellFormItem);
    }

    @DeleteMapping("/item/delete/{id}")
    public Result deleteItem(@PathVariable Integer id){
        sellService.deleteItemById(id);
        return Result.success();
    }

    @PutMapping("/item/update")
    public Result updateItem(@RequestBody SellFormItem sellFormItem){
        sellService.updateItemById(sellFormItem);
        return Result.success();
    }

    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange){
        return Result.success(sellService.getCountByTimeRange(timeRange));
    }
}
