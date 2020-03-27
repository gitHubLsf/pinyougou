package com.lsf.pinyougou.content.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbContentDao;
import com.lsf.pinyougou.pojo.TbContent;
import com.lsf.pinyougou.content.service.ContentService;
import vo.PageResult;


/**
 * 广告服务实现层
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentDao tbContentDao;


    @Override
    public List<TbContent> findAll() {
        return tbContentDao.queryAll(null);
    }


    @Override
    public PageResult findPageLimit(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbContent> list = tbContentDao.queryAll(content);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    @Override
    public void add(TbContent content) {
        tbContentDao.insert(content);
    }


    @Override
    public void update(TbContent content) {
        tbContentDao.update(content);
    }


    @Override
    public TbContent findOne(long id) {
        return tbContentDao.queryById(id);
    }


    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbContentDao.deleteById(id);
        }
    }

}
