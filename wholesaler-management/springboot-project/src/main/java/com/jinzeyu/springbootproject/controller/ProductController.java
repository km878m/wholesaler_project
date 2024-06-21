package com.jinzeyu.springbootproject.controller;

import com.jinzeyu.springbootproject.anno.Log;
import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.request.ProductPageRequest;
import com.jinzeyu.springbootproject.pojo.Product;
import com.jinzeyu.springbootproject.service.impl.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Log
    @PostMapping("/save")
    public Result save(@RequestBody Product product){
        productService.save(product);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Product> products = productService.list();
        return Result.success(products);
    }

    @GetMapping("/page")
    public Result page(ProductPageRequest productPageRequest){
        return Result.success(productService.page(productPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Product product = productService.getById(id);
        return Result.success(product);
    }

    @Log
    @PutMapping("/update")
    public Result update(@RequestBody Product product){
        productService.update(product);
        return Result.success();
    }

    @Log
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        productService.deleteById(id);
        return Result.success();
    }
}
