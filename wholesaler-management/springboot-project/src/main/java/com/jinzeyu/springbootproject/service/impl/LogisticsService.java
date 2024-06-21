package com.jinzeyu.springbootproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.request.LogisticsPageRequest;
import com.jinzeyu.springbootproject.mapper.LogisticsMapper;
import com.jinzeyu.springbootproject.pojo.Logistics;
import com.jinzeyu.springbootproject.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogisticsService implements ILogisticsService {
    @Autowired
    private LogisticsMapper logisticsMapper;

    // 查询所有物流方法
    @Override
    public List<Logistics> list() {
        return logisticsMapper.list();
    }

    // 物流分页方法
    @Override
    public Object page(LogisticsPageRequest logisticsPageRequest) {
        PageHelper.startPage(logisticsPageRequest.getPageNum(),logisticsPageRequest.getPageSize());
        List<Logistics> logistics = logisticsMapper.listByCondition(logisticsPageRequest);
        PageInfo<Logistics> info = new PageInfo<>(logistics);
        return info;
    }

    // 添加物流方法
    @Override
    public void save(Logistics logistics) {
        logisticsMapper.save(logistics);
    }

    // 根据id查询物流方法（用于数据回显）
    @Override
    public Logistics getById(Integer id) {
        return logisticsMapper.getById(id);
    }

    // 更新物流信息方法
    @Override
    public void update(Logistics logistics) {
        logistics.setUpdateTime(new Date());
        logisticsMapper.updateById(logistics);
    }

    // 根据id删除物流方法
    @Override
    public void deleteById(Integer id) {
        logisticsMapper.deleteById(id);
    }
}
