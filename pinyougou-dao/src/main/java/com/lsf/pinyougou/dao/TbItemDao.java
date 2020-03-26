package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbItem;

import java.util.List;


public interface TbItemDao {


    TbItem queryById(Long id);


    List<TbItem> queryAll(TbItem tbItem);


    int insert(TbItem tbItem);


    int update(TbItem tbItem);


    int deleteById(Long id);


    /**
     * 通过商品 ID 删除商品 SKU
     *
     * @param id
     */
    void deleteByGoodsId(Long id);
}