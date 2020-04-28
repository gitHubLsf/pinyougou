package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbItem;
import org.apache.ibatis.annotations.Param;

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


    /**
     * 批量编辑商品 SKU，修改 status 字段
     */
    int batchEditItem(@Param("ids") Long[] ids, @Param("status") String status);


    /**
     * 批量根据商品 SPU ID 查询商品 SKU，SKU 的状态 status 要等于 1
     */
    List<TbItem> batchSearchItemByGoodId(@Param("ids") Long[] ids, @Param("status") String status);

}