package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.request.CategoryPageRequest;
import com.jinzeyu.springbootproject.pojo.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> list();

    Object page(CategoryPageRequest categoryPageRequest);

    void save(Category category);

    Category getById(Integer id);

    void update(Category category);

    void deleteById(Integer id);
}
