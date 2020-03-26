package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbProvinces;

import java.util.List;


public interface TbProvincesDao {


    TbProvinces queryById(Integer id);


    List<TbProvinces> queryAll(TbProvinces tbProvinces);


    int insert(TbProvinces tbProvinces);


    int update(TbProvinces tbProvinces);


    int deleteById(Integer id);

}