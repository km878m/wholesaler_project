package com.jinzeyu.springbootproject.controller;

import com.jinzeyu.springbootproject.anno.Log;
import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.request.LogisticsPageRequest;
import com.jinzeyu.springbootproject.pojo.Logistics;
import com.jinzeyu.springbootproject.service.impl.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/logistics")
public class LogisticsController {
    @Autowired
    private LogisticsService logisticsService;

    @Log
    @PostMapping("/save")
    public Result save(@RequestBody Logistics logistics){
        logisticsService.save(logistics);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Logistics> logistics = logisticsService.list();
        return Result.success(logistics);
    }

    @GetMapping("/page")
    public Result page(LogisticsPageRequest logisticsPageRequest){
        return Result.success(logisticsService.page(logisticsPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Logistics logistics = logisticsService.getById(id);
        return Result.success(logistics);
    }

    @Log
    @PutMapping("/update")
    public Result update(@RequestBody Logistics logistics){
        logisticsService.update(logistics);
        return Result.success();
    }

    @Log
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        logisticsService.deleteById(id);
        return Result.success();
    }
}
