package com.jinzeyu.springbootproject.controller;

import com.jinzeyu.springbootproject.anno.Log;
import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.request.CustomerPageRequest;
import com.jinzeyu.springbootproject.controller.request.TransporterPageRequest;
import com.jinzeyu.springbootproject.pojo.Customer;
import com.jinzeyu.springbootproject.pojo.Transporter;
import com.jinzeyu.springbootproject.service.impl.CustomerService;
import com.jinzeyu.springbootproject.service.impl.TransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/transporter")
public class TransporterController {
    @Autowired
    private TransporterService transporterService;

    @Log
    @PostMapping("/save")
    public Result save(@RequestBody Transporter transporter){
        transporterService.save(transporter);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Transporter> transporters = transporterService.list();
        return Result.success(transporters);
    }

    @GetMapping("/page")
    public Result page(TransporterPageRequest transporterPageRequest){
        return Result.success(transporterService.page(transporterPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Transporter transporter = transporterService.getById(id);
        return Result.success(transporter);
    }

    @Log
    @PutMapping("/update")
    public Result update(@RequestBody Transporter transporter){
        transporterService.update(transporter);
        return Result.success();
    }

    @Log
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        transporterService.deleteById(id);
        return Result.success();
    }
}
