package com.lsf.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbGoodsDescDao;
import com.lsf.pinyougou.pojo.TbGoodsDesc;
import com.lsf.pinyougou.service.interfaces.sellergoods.GoodsDescService;
import org.springframework.beans.factory.annotation.Autowired;
import vo.PageResult;

import java.util.List;


/**
 * 服务实现层
 */
@Service
public class GoodsDescServiceImpl implements GoodsDescService {

    /**
     * 查询全部
     */
    @Override
    public List<TbGoodsDesc> findAll() {
        return tbGoodsDescDao.queryAll(null);
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbGoodsDesc goodsDesc, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbGoodsDesc> list = tbGoodsDescDao.queryAll(goodsDesc);
        PageInfo<TbGoodsDesc> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加
     */
    @Override
    public void add(TbGoodsDesc goodsDesc) {
        tbGoodsDescDao.insert(goodsDesc);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbGoodsDesc goodsDesc) {
        tbGoodsDescDao.update(goodsDesc);
    }


    /**
     * 根据 ID 获取实体
     */
    @Override
    public TbGoodsDesc findOne(long id) {
        return tbGoodsDescDao.queryById(id);
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        if (ids != null && ids.length > 0)
            tbGoodsDescDao.batchDeleteGoodsDesc(ids);
    }


    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbGoodsDescDao tbGoodsDescDao;
}
