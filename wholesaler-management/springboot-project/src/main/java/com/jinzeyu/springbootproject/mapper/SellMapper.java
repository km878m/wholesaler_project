package com.jinzeyu.springbootproject.mapper;

import com.jinzeyu.springbootproject.controller.request.SellFormPageRequest;
import com.jinzeyu.springbootproject.pojo.SellForm;
import com.jinzeyu.springbootproject.pojo.SellFormItem;
import com.jinzeyu.springbootproject.pojo.SellReturnCountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellMapper {
    void save(SellForm sellForm);

    SellForm byId(Integer id);

    Integer returnId(String sellNumber);

    void saveItem(SellFormItem sellFormItem);

    void deleteById(Integer id);

    void deleteByPid(Integer id);

    SellFormItem itemById(Integer id);

    void deleteItemById(Integer id);

    void updateItemById(SellFormItem sellFormItem);

    void updateById(SellForm sellForm);

    List<SellForm> listByCondition(SellFormPageRequest sellFormPageRequest);

    Integer selectNumberById(Integer id);

    Integer selectTotalPricesById(Integer id);

    void updateNumberById(Integer id, Integer number);

    void updateTotalPricesById(Integer id, Integer totalPrices);

    boolean selectOutTimeFlagById(Integer id);

    List<SellReturnCountPO> getPriceCountByTimeRange(@Param("timeRange") String timeRange);

    List<SellReturnCountPO> getNumberCountByTimeRange(@Param("timeRange") String timeRange);
}
