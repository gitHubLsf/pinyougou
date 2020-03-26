package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbFreightTemplate;

import java.util.List;


public interface TbFreightTemplateDao {


    TbFreightTemplate queryById(Long id);


    List<TbFreightTemplate> queryAll(TbFreightTemplate tbFreightTemplate);


    int insert(TbFreightTemplate tbFreightTemplate);


    int update(TbFreightTemplate tbFreightTemplate);


    int deleteById(Long id);

}