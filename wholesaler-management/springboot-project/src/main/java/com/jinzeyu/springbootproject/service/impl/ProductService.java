package com.jinzeyu.springbootproject.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.request.ProductPageRequest;
import com.jinzeyu.springbootproject.mapper.ProductMapper;
import com.jinzeyu.springbootproject.pojo.Product;
import com.jinzeyu.springbootproject.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ProductService implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    // 查询所有产品方法
    @Override
    public List<Product> list() {
        return productMapper.list();
    }

    // 产品分页方法
    @Override
    public Object page(ProductPageRequest productPageRequest) {
        PageHelper.startPage(productPageRequest.getPageNum(),productPageRequest.getPageSize());
        List<Product> products = productMapper.listByCondition(productPageRequest);
        PageInfo<Product> info = new PageInfo<>(products);
        return info;
    }

    // 添加产品方法
    @Override
    public void save(Product product) {
        //分类数组转成字符串
//        List<String> categories =  product.getCategories();
//        StringBuilder sb = new StringBuilder();
//        if(CollUtil.isNotEmpty(categories)){
//            categories.forEach(v -> sb.append(v).append("/"));
//            product.setCategory(sb.toString().substring(0,sb.lastIndexOf("/")));
//        }
        productMapper.save(product);
    }

    // 根据id查询产品方法（用于数据回显）
    @Override
    public Product getById(Integer id) {
        return productMapper.getById(id);
    }

    // 更新产品信息方法
    @Override
    public void update(Product product) {
        product.setUpdateTime(LocalDate.now());
        productMapper.updateById(product);
    }

    // 根据id删除产品方法
    @Override
    public void deleteById(Integer id) {
        productMapper.deleteById(id);
    }

}
