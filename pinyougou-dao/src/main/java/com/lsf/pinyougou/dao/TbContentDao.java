package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbContent;
import org.apache.ibatis.annotations.Param;

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


    /**
     * 查询这些广告对应的广告分类集合（使用分组查询）
     */
    List<Long> findContentsCategoryList(@Param("ids") Long[] ids);


    /**
     * 批量删除广告
     */
    int batchDeleteContent(@Param("ids") Long[] ids);
}