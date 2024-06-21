package com.jinzeyu.springbootproject.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinzeyu.springbootproject.controller.request.SellFormPageRequest;
import com.jinzeyu.springbootproject.exception.ServiceException;
import com.jinzeyu.springbootproject.mapper.OrderMapper;
import com.jinzeyu.springbootproject.mapper.ProductMapper;
import com.jinzeyu.springbootproject.mapper.SellMapper;
import com.jinzeyu.springbootproject.pojo.OrderFormItem;
import com.jinzeyu.springbootproject.pojo.SellForm;
import com.jinzeyu.springbootproject.pojo.SellFormItem;
import com.jinzeyu.springbootproject.pojo.SellReturnCountPO;
import com.jinzeyu.springbootproject.service.ISellService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class SellService implements ISellService {
    @Autowired
    private SellMapper sellMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    // 销售单创建方法
    @Override
    public Integer save(SellForm sellForm) {
        //时间:精确到毫秒
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String localDate = LocalDateTime.now().format(ofPattern);
        //3位随机数
        String randomNumeric = RandomStringUtils.randomNumeric(3);
        String sellNumber = localDate + randomNumeric;
        sellForm.setSellNumber(sellNumber);

        sellMapper.save(sellForm);
        Integer id = sellMapper.returnId(sellNumber);
        return id;
    }

    // 根据id查询销售单方法（用于数据回显）
    @Override
    public SellForm byId(Integer id) {
        SellForm sellForm = sellMapper.byId(id);
        return sellForm;
    }

    // 添加销售单单项方法
    @Override
    public void saveItem(SellFormItem sellFormItem) {
        Integer price = productMapper.getPriceByName(sellFormItem.getProductName());
        Integer haveNumber = productMapper.getNumberByName(sellFormItem.getProductName());
        String description = productMapper.getDescriptionByName(sellFormItem.getProductName());
        if(sellFormItem.getNumber()>haveNumber){
            throw new ServiceException("产品数量不足，请联系仓库管理员补货");
        }

        //插入标准销售价
        sellFormItem.setPrice(price);
        //插入产品规格
        sellFormItem.setProductDescription(description);
        //插入产品总价格
        Integer totalPrices = price*sellFormItem.getNumber();
        sellFormItem.setTotalPrices(totalPrices);

        //增加销售单总件数
        Integer number = sellMapper.selectNumberById(sellFormItem.getPid());
        sellMapper.updateNumberById(sellFormItem.getPid(),number + sellFormItem.getNumber());

        //增加销售单总价
        Integer formTotalPrices = sellMapper.selectTotalPricesById(sellFormItem.getPid());
        sellMapper.updateTotalPricesById(sellFormItem.getPid(),formTotalPrices + totalPrices);

        sellMapper.saveItem(sellFormItem);
    }

    // 根据id删除销售单方法
    @Override
    public void deleteById(Integer id) {
        sellMapper.deleteById(id);
        sellMapper.deleteByPid(id);
    }

    // 根据id查询销售单单项方法（用于数据回显）
    @Override
    public SellFormItem itemById(Integer id) {
        return sellMapper.itemById(id);
    }

    // 根据id删除销售单单项方法
    @Override
    public void deleteItemById(Integer id) {
        SellFormItem sellFormItem = sellMapper.itemById(id);
        //减少采购单总件数
        Integer number = sellMapper.selectNumberById(sellFormItem.getPid());
        sellMapper.updateNumberById(sellFormItem.getPid(),number - sellFormItem.getNumber());

        //减少销售单总价
        Integer formTotalPrices = sellMapper.selectTotalPricesById(sellFormItem.getPid());
        sellMapper.updateTotalPricesById(sellFormItem.getPid(),formTotalPrices - sellFormItem.getTotalPrices());

        sellMapper.deleteItemById(id);
    }

    // 编辑销售单单项方法
    @Override
    public void updateItemById(SellFormItem sellFormItem) {
        Integer totalPrices = sellFormItem.getPrice()*sellFormItem.getNumber();
        sellFormItem.setTotalPrices(totalPrices);
        sellMapper.updateItemById(sellFormItem);
    }

    // 销售单确认发货方法
    @Override
    public void updateById(SellForm sellForm) {
        sellForm.setUpdateTime(LocalDate.now());
        sellMapper.updateById(sellForm);
        boolean outTimeFlag = sellForm.isOutTimeFlag();
        //减少库存
        //boolean outTimeFlag = sellMapper.selectOutTimeFlagById(sellForm.getId());
        if(outTimeFlag) {
            sellForm = sellMapper.byId(sellForm.getId());
            sellForm.setOutTime(LocalDateTime.now());
            Iterator<SellFormItem> iter = sellForm.getChildren().iterator();
            while (iter.hasNext()) {
                SellFormItem sellFormItem = iter.next();
                String productName = sellFormItem.getProductName();
                Integer number = productMapper.getNumberByName(productName);
                productMapper.updateNumberByName(productName, number - sellFormItem.getNumber());
            }
            sellMapper.updateById(sellForm);
        }
    }

    // 销售单分页方法
    @Override
    public Object page(SellFormPageRequest sellFormPageRequest) {
        PageHelper.startPage(sellFormPageRequest.getPageNum(),sellFormPageRequest.getPageSize());
        List<SellForm> sellForms = sellMapper.listByCondition(sellFormPageRequest);
        PageInfo<SellForm> info = new PageInfo<>(sellForms);
        return info;
    }

    //展示首页数据
    @Override
    public Map<String, Object> getCountByTimeRange(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange)
        {
            case "week":
                // offsetDay 计算时间的一个工具方法
                // rangeToList 返回从开始时间到结束时间的一个时间范围
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -6), today, DateField.DAY_OF_WEEK);
                break;
            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -29), today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -59), today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -89), today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        // datetimeToDateStr 把 DateTime 类型的List转换成一个 String的List
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date",dateStrRange); // x轴的日期数据生产完毕
        List<SellReturnCountPO> priceCount = sellMapper.getPriceCountByTimeRange(timeRange);
        //List<SellReturnCountPO> numberCount = sellMapper.getNumberCountByTimeRange(timeRange);
        List<SellReturnCountPO> priceICount = orderMapper.getPriceCountByTimeRange(timeRange);
        map.put("price", countList(priceCount, dateStrRange));
        //map.put("number", countList(numberCount, dateStrRange));
        map.put("priceI", countList(priceICount, dateStrRange));
        return map;
    }

    //将日期转化成字符串的形式
    private List<String> datetimeToDateStr(List<DateTime> dateTimeList){
        List<String> list = CollUtil.newArrayList();
        if(CollUtil.isEmpty(dateTimeList)) {
            return list;
        }
        for(DateTime dateTime : dateTimeList) {
            String date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }

    // 对数据库未统计的时间进行处理
    private List<Integer> countList(List<SellReturnCountPO> countPOList, List<String> dateRange) {
        List<Integer> list = CollUtil.newArrayList();
        if(CollUtil.isEmpty(countPOList)){
            return list;
        }
        for(String date : dateRange){
            // .map(SellReturnCountPO::getCount) 取出对象里的 count值
            // orElse(0) 对没匹配的数据返回0
            Integer count = countPOList.stream().filter(countPO -> date.equals(countPO.getDate()))
                    .map(SellReturnCountPO::getCount).findFirst().orElse(0);
            list.add(count);
        }
        // 最后返回的list的元素个数会跟 dateRange 的元素个数完全一样
        return list;
    }
}
