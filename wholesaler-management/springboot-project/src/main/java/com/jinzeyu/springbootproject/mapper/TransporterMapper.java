package com.jinzeyu.springbootproject.mapper;

import com.jinzeyu.springbootproject.controller.request.TransporterPageRequest;
import com.jinzeyu.springbootproject.pojo.Transporter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransporterMapper {

    List<Transporter> list();

    List<Transporter> listByCondition(TransporterPageRequest transporterPageRequest);

    void save(Transporter transporter);

    Transporter getById(Integer id);

    void updateById(Transporter transporter);

    void deleteById(Integer id);
}
