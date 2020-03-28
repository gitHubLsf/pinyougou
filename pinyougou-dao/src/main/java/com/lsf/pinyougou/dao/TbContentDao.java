package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbContent;

import java.util.List;


public interface TbContentDao {


    TbContent queryById(Long id);


    List<TbContent> queryAll(TbContent tbContent);


    int insert(TbContent tbContent);


    int update(TbContent tbContent);


    int deleteById(Long id);

    /**
     * 查询指定广告分类 ID 下的所有广告，只查有效的广告，升序排列
     */
    List<TbContent> findByContentCategoryId(TbContent content);
}