package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbCities;

import java.util.List;


public interface TbCitiesDao {


    TbCities queryById(Integer id);


    List<TbCities> queryAll(TbCities tbCities);


    int insert(TbCities tbCities);


    int update(TbCities tbCities);


    int deleteById(Integer id);

}