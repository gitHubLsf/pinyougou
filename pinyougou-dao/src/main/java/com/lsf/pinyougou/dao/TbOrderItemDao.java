package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbOrderItem;

import java.util.List;


public interface TbOrderItemDao {


    TbOrderItem queryById(Long id);


    List<TbOrderItem> queryAll(TbOrderItem tbOrderItem);


    int insert(TbOrderItem tbOrderItem);


    int update(TbOrderItem tbOrderItem);


    int deleteById(Long id);

}