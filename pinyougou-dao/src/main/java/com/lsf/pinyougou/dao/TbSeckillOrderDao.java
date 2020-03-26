package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbSeckillOrder;

import java.util.List;


public interface TbSeckillOrderDao {


    TbSeckillOrder queryById(Long id);


    List<TbSeckillOrder> queryAll(TbSeckillOrder tbSeckillOrder);


    int insert(TbSeckillOrder tbSeckillOrder);


    int update(TbSeckillOrder tbSeckillOrder);


    int deleteById(Long id);

}