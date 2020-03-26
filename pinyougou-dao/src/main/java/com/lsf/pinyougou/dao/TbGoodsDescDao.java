package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbGoodsDesc;

import java.util.List;


public interface TbGoodsDescDao {


    TbGoodsDesc queryById(Long goodsId);


    List<TbGoodsDesc> queryAll(TbGoodsDesc tbGoodsDesc);


    int insert(TbGoodsDesc tbGoodsDesc);


    int update(TbGoodsDesc tbGoodsDesc);


    int deleteById(Long goodsId);

}