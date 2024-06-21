package com.jinzeyu.springbootproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.request.TransporterPageRequest;
import com.jinzeyu.springbootproject.mapper.TransporterMapper;
import com.jinzeyu.springbootproject.pojo.Transporter;
import com.jinzeyu.springbootproject.service.ITransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransporterService implements ITransporterService {
    @Autowired
    private TransporterMapper transporterMapper;

    // 查询所有运输负责人方法
    @Override
    public List<Transporter> list() {
        return transporterMapper.list();
    }

    // 运输负责人分页方法
    @Override
    public Object page(TransporterPageRequest transporterPageRequest) {
        PageHelper.startPage(transporterPageRequest.getPageNum(),transporterPageRequest.getPageSize());
        List<Transporter> transporters = transporterMapper.listByCondition(transporterPageRequest);
        PageInfo<Transporter> info = new PageInfo<>(transporters);
        return info;
    }

    // 添加运输负责人方法
    @Override
    public void save(Transporter transporter) {
        transporterMapper.save(transporter);
    }

    // 根据id查询运输负责人（用于数据回显）
    @Override
    public Transporter getById(Integer id) {
        return transporterMapper.getById(id);
    }

    // 编辑运输负责人信息方法
    @Override
    public void update(Transporter transporter) {
        transporter.setUpdateTime(new Date());
        transporterMapper.updateById(transporter);
    }

    // 根据id删除运输负责人方法
    @Override
    public void deleteById(Integer id) {
        transporterMapper.deleteById(id);
    }
}
