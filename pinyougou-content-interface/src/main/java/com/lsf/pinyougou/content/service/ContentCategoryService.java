package com.lsf.pinyougou.content.service;

import java.util.List;
import java.util.Map;

import com.lsf.pinyougou.pojo.TbContentCategory;
import vo.PageResult;


/**
 * 广告分类服务层接口
 */
public interface ContentCategoryService {

    List<TbContentCategory> findAll();


    PageResult findPageLimit(TbContentCategory contentCategory, int pageNum, int pageSize);


    void add(TbContentCategory contentCategory);


    void update(TbContentCategory contentCategory);


    TbContentCategory findOne(long id);


    void batchDelete(Long[] ids);
}
