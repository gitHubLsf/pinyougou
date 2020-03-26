package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbGoods;

import java.util.List;


public interface TbGoodsDao {

    TbGoods queryById(Long id);


    List<TbGoods> queryAll(TbGoods tbGoods);


    int insert(TbGoods tbGoods);


    int update(TbGoods tbGoods);


    int deleteById(Long id);

}