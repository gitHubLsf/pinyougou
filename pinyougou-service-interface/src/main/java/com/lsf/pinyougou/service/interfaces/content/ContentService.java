package com.lsf.pinyougou.service.interfaces.content;

import com.lsf.pinyougou.pojo.TbContent;
import vo.PageResult;

import java.util.List;


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
