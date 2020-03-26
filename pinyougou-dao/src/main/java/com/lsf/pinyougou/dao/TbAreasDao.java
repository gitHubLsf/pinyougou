package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbAreas;

import java.util.List;


public interface TbAreasDao {


    TbAreas queryById(Integer id);


    List<TbAreas> queryAll(TbAreas tbAreas);


    int insert(TbAreas tbAreas);


    int update(TbAreas tbAreas);


    int deleteById(Integer id);

}