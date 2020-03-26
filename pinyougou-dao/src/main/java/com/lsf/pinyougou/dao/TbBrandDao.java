package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbBrand;

import java.util.List;
import java.util.Map;


public interface TbBrandDao {


    TbBrand queryById(Long id);


    List<TbBrand> queryAll(TbBrand tbBrand);


    List<TbBrand> queryAllLimit(TbBrand tbBrand);


    int insert(TbBrand tbBrand);


    int update(TbBrand tbBrand);


    int deleteById(Long id);


    /**
     * 查询所有品牌下拉列表
     *
     * @return
     */
    List<Map> selectBrandList();

}