package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbSeller;

import java.util.List;


public interface TbSellerDao {


    TbSeller queryById(String sellerId);


    List<TbSeller> queryAll(TbSeller tbSeller);


    int insert(TbSeller tbSeller);


    int update(TbSeller tbSeller);


    int deleteById(String sellerId);


    /**
     * 多条件分页查询未审核商家列表
     *
     * @param seller
     * @return
     */
    List<TbSeller> queryAllLimit(TbSeller seller);
}