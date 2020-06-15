package com.lsf.pinyougou.service.interfaces.content;

import com.lsf.pinyougou.pojo.TbContentCategory;
import vo.PageResult;

import java.util.List;


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
