package com.jinzeyu.springbootproject.mapper;

import com.jinzeyu.springbootproject.controller.request.LogisticsPageRequest;
import com.jinzeyu.springbootproject.controller.request.TransporterPageRequest;
import com.jinzeyu.springbootproject.pojo.Logistics;
import com.jinzeyu.springbootproject.pojo.Transporter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogisticsMapper {

    List<Logistics> list();

    List<Logistics> listByCondition(LogisticsPageRequest logisticsPageRequest);

    void save(Logistics logistics);

    Logistics getById(Integer id);

    void updateById(Logistics logistics);

    void deleteById(Integer id);
}
