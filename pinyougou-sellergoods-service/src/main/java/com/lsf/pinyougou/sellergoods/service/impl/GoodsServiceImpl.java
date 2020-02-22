package com.lsf.pinyougou.sellergoods.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbGoodsDescDao;
import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojo.TbGoodsDesc;
import com.lsf.pinyougou.pojogroup.Goods;
import com.lsf.pinyougou.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbGoodsDao;

import org.springframework.security.core.context.SecurityContextHolder;
import vo.PageResult;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbGoodsDao tbGoodsDao;


    /**
     * 商品扩展数据访问层对象
     */
    @Autowired
    private TbGoodsDescDao tbGoodsDescDao;


    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return tbGoodsDao.queryAll(null);
    }


    /**
     * 无条件分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbGoods> list = tbGoodsDao.queryAll(null);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbGoods> list = tbGoodsDao.queryAll(goods);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加商品
     */
    @Override
    public void add(Goods goods) {
        // 设置商品的状态为 未审核
        goods.getTbGoods().setAuditStatus("0");

        // 添加商品，同时返回商品 ID
        tbGoodsDao.insert(goods.getTbGoods());

        // 设置商品扩展的 ID 为商品 ID
        goods.getTbGoodsDesc().setGoodsId(goods.getTbGoods().getId());

        // 添加商品扩展
        tbGoodsDescDao.insert(goods.getTbGoodsDesc());
    }


    /**
     * 修改
     */
    @Override
    public void update(TbGoods goods) {
        tbGoodsDao.update(goods);
    }


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbGoods findOne(long id) {
        return tbGoodsDao.queryById(id);
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbGoodsDao.deleteById(id);
        }
    }

}
