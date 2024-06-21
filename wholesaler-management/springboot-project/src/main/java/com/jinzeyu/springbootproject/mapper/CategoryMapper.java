package com.jinzeyu.springbootproject.mapper;

import com.jinzeyu.springbootproject.controller.request.CategoryPageRequest;
import com.jinzeyu.springbootproject.pojo.Category;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> list();

    List<Category> listByCondition(CategoryPageRequest categoryPageRequest);

    void save(Category category);

    Category getById(Integer id);

    void updateById(Category category);

    void deleteById(Integer id);

}
