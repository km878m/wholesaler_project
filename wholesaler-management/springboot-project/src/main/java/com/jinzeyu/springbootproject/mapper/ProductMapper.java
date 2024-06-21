package com.jinzeyu.springbootproject.mapper;


import com.jinzeyu.springbootproject.controller.request.ProductPageRequest;
import com.jinzeyu.springbootproject.pojo.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> list();

    List<Product> listByCondition(ProductPageRequest productPageRequest);

    void save(Product product);

    Product getById(Integer id);

    void updateById(Product product);

    void deleteById(Integer id);

    Integer getPriceByName(String productName);

    Integer getNumberByName(String productName);

    String getDescriptionByName(String productName);

    void updateNumberByName(String productName, Integer number);
}
