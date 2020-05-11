package com.lsf.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbItemDao;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.sellergoods.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import vo.PageResult;

import java.util.List;


/**
 * 商品 SKU 服务实现层
 */
@Service
public class ItemServiceImpl implements ItemService {

    /**
     * 查询全部
     */
    @Override
    public List<TbItem> findAll() {
        return tbItemDao.queryAll(null);
    }


    /**
     * 无条件分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbItem> list = tbItemDao.queryAll(null);
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbItem item, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbItem> list = tbItemDao.queryAll(item);
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加
     */
    @Override
    public void add(TbItem item) {
        tbItemDao.insert(item);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbItem item) {
        tbItemDao.update(item);
    }


    /**
     * 根据 ID 获取实体
     */
    @Override
    public TbItem findOne(long id) {
        return tbItemDao.queryById(id);
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        if (ids != null && ids.length > 0)
            tbItemDao.batchEditItem(ids, "3");
    }


    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbItemDao tbItemDao;
}
