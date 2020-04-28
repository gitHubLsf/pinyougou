package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TbGoodsDao {

    TbGoods queryById(Long id);


    List<TbGoods> queryAll(TbGoods tbGoods);


    int insert(TbGoods tbGoods);


    int update(TbGoods tbGoods);


    int deleteById(Long id);


    /**
     * 批量删除商品，只进行逻辑删除（将商品的 is_delete 字段设置为 "1"）
     */
    int batchDeleteGoods(@Param("ids") Long[] ids);


    /**
     * 批量修改商品的审核状态
     */
    int batchUpdateGoodsAuditStatus(@Param("ids") Long[] ids, @Param("status") String status);


    /**
     * 批量修改商品的上下架状态，前提是商品已通过审核
     */
    int batchUpdateGoodsMarketAble(@Param("ids") Long[] ids, @Param("status") String status);

}