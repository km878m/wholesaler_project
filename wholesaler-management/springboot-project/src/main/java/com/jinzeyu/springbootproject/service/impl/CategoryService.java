package com.jinzeyu.springbootproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.request.CategoryPageRequest;
import com.jinzeyu.springbootproject.mapper.CategoryMapper;
import com.jinzeyu.springbootproject.pojo.Category;
import com.jinzeyu.springbootproject.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    // 查询所有产品分类方法
    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    // 产品分类分页方法
    @Override
    public Object page(CategoryPageRequest categoryPageRequest) {
        PageHelper.startPage(categoryPageRequest.getPageNum(),categoryPageRequest.getPageSize());
        //自关联查询
        List<Category> categories = categoryMapper.listByCondition(categoryPageRequest);
        PageInfo<Category> info = new PageInfo<>(categories);
        return info;
    }

    // 产品分类添加方法
    @Override
    public void save(Category category) {
        categoryMapper.save(category);
    }

    // 根据id查询查询产品分类方法（用于数据回显）
    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    // 更新产品分类方法
    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDate.now());
        categoryMapper.updateById(category);
    }

    // 根据id删除产品分类方法
    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

}
