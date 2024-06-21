package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.request.LogisticsPageRequest;
import com.jinzeyu.springbootproject.controller.request.TransporterPageRequest;
import com.jinzeyu.springbootproject.pojo.Logistics;
import com.jinzeyu.springbootproject.pojo.Transporter;

import java.util.List;

public interface ILogisticsService {
    List<Logistics> list();

    Object page(LogisticsPageRequest logisticsPageRequest);

    void save(Logistics logistics);

    Logistics getById(Integer id);

    void update(Logistics logistics);

    void deleteById(Integer id);
}
