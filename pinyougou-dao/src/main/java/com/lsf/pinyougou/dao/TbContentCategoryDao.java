package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbContentCategory;

import java.util.List;
import java.util.Map;


public interface TbContentCategoryDao {


    TbContentCategory queryById(Long id);


    List<TbContentCategory> queryAll(TbContentCategory tbContentCategory);


    int insert(TbContentCategory tbContentCategory);


    int update(TbContentCategory tbContentCategory);


    int deleteById(Long id);

}