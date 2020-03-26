package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbItemCat;
import com.lsf.pinyougou.pojogroup.TbItemCats;

import java.util.List;


public interface TbItemCatDao {


    TbItemCats queryById(Long id);


    List<TbItemCat> queryAll(TbItemCat tbItemCat);


    int insert(TbItemCat tbItemCat);


    int update(TbItemCat tbItemCat);


    int deleteById(Long id);

}