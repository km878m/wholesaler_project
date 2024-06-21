package com.jinzeyu.springbootproject.service;

import com.jinzeyu.springbootproject.controller.request.CustomerPageRequest;
import com.jinzeyu.springbootproject.controller.request.TransporterPageRequest;
import com.jinzeyu.springbootproject.pojo.Customer;
import com.jinzeyu.springbootproject.pojo.Transporter;

import java.util.List;

public interface ITransporterService {
    List<Transporter> list();

    Object page(TransporterPageRequest transporterPageRequest);

    void save(Transporter transporter);

    Transporter getById(Integer id);

    void update(Transporter transporter);

    void deleteById(Integer id);
}
