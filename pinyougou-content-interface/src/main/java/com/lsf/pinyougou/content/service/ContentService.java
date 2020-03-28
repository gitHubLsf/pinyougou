package com.lsf.pinyougou.content.service;

import java.util.List;

import com.lsf.pinyougou.pojo.TbContent;
import vo.PageResult;


/**
 * 广告服务层接口
 */
public interface ContentService {

    List<TbContent> findAll();


    PageResult findPageLimit(TbContent content, int pageNum, int pageSize);


    void add(TbContent content);


    void update(TbContent content);


    TbContent findOne(long id);


    void batchDelete(Long[] ids);


    List<TbContent> findByContentCategoryId(Long contentCategoryId);
}
