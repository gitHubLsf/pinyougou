package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbContentCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface TbContentCategoryDao {


    TbContentCategory queryById(Long id);


    List<TbContentCategory> queryAll(TbContentCategory tbContentCategory);


    int insert(TbContentCategory tbContentCategory);


    int update(TbContentCategory tbContentCategory);


    int deleteById(Long id);


    /**
     * 通过广告分类 ID 批量删除广告分类
     */
    int batchDeleteById(@Param("ids") Long[] ids);

}