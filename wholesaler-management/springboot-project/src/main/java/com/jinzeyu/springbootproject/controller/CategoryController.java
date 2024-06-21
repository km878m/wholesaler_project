package com.jinzeyu.springbootproject.controller;

import cn.hutool.core.collection.CollUtil;
import com.jinzeyu.springbootproject.anno.Log;
import com.jinzeyu.springbootproject.common.Result;
import com.jinzeyu.springbootproject.controller.request.CategoryPageRequest;
import com.jinzeyu.springbootproject.pojo.Category;
import com.jinzeyu.springbootproject.service.impl.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Log
    @PostMapping("/save")
    public Result save(@RequestBody Category category){
        categoryService.save(category);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }

    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest){
        return Result.success(categoryService.page(categoryPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @Log
    @PutMapping("/update")
    public Result update(@RequestBody Category category){
        categoryService.update(category);
        return Result.success();
    }

    @Log
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }

//    @GetMapping("/tree")
//    public Result tree(){
//        List<Category> list = categoryService.list();
//        return Result.success(createTree(null,list));
//    }

    // 完全递归的方法来实现递归树
//    private List<Category> createTree(Integer pid, List<Category> categories){
//        List<Category> treeList = new ArrayList<>();
//        for(Category category : categories){
//            if(pid == null){
//                if(category.getPid() == null){ //那这就是第一级节点
//                    treeList.add(category);
//                    category.setChildren(createTree(category.getId(),categories));
//                }
//            }else{
//                if(pid.equals(category.getPid())){
//                    treeList.add(category);
//                    category.setChildren(createTree(category.getId(),categories));
//                }
//            }
//            if(CollUtil.isEmpty(category.getChildren())){
//               category.setChildren(null);
//            }
//        }
//        return treeList;
//    }
}
