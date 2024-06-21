package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.request.CategoryPageRequest;
import com.jinzeyu.springbootproject.controller.request.ProductPageRequest;
import com.jinzeyu.springbootproject.pojo.Category;
import com.jinzeyu.springbootproject.pojo.Product;

import java.util.List;

public interface IProductService {
    List<Product> list();

    Object page(ProductPageRequest productPageRequest);

    void save(Product product);

    Product getById(Integer id);

    void update(Product product);

    void deleteById(Integer id);
}
