package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbOrder;

import java.util.List;


public interface TbOrderDao {


    TbOrder queryById(Long orderId);


    List<TbOrder> queryAll(TbOrder tbOrder);


    int insert(TbOrder tbOrder);


    int update(TbOrder tbOrder);


    int deleteById(Long orderId);

}