package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TbOrderDao {


    TbOrder queryById(Long orderId);


    List<TbOrder> queryAll(TbOrder tbOrder);


    int insert(TbOrder tbOrder);


    int update(TbOrder tbOrder);


    int deleteById(Long orderId);


    /**
     * 批量修改订单主表中的状态
     *
     * @param orderIds  订单号数组
     */
    void batchUpdateStatusByOrderId(@Param("orderIds") Long[] orderIds, @Param("status") String status);
}