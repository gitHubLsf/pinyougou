package com.lsf.pinyougou.content.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbContentCategoryDao;
import com.lsf.pinyougou.pojo.TbContentCategory;
import com.lsf.pinyougou.service.interfaces.content.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import vo.PageResult;

import java.util.List;


/**
 * 广告分类服务实现层
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Override
    public List<TbContentCategory> findAll() {
        return tbContentCategoryDao.queryAll(null);
    }


    @Override
    public PageResult findPageLimit(TbContentCategory contentCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbContentCategory> list = tbContentCategoryDao.queryAll(contentCategory);
        PageInfo<TbContentCategory> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    @Override
    public void add(TbContentCategory contentCategory) {
        tbContentCategoryDao.insert(contentCategory);
    }


    @Override
    public void update(TbContentCategory contentCategory) {
        tbContentCategoryDao.update(contentCategory);
    }


    @Override
    public TbContentCategory findOne(long id) {
        return tbContentCategoryDao.queryById(id);
    }


    @Override
    public void batchDelete(Long[] ids) {
        if (ids != null && ids.length > 0)
            tbContentCategoryDao.batchDeleteById(ids);
    }


    @Autowired
    private TbContentCategoryDao tbContentCategoryDao;

}
