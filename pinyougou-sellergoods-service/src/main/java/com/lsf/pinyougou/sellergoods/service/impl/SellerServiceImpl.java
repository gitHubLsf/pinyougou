package com.lsf.pinyougou.sellergoods.service.impl;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.sellergoods.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbSellerDao;
import com.lsf.pinyougou.pojo.TbSeller;
import vo.PageResult;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SellerServiceImpl implements SellerService {

    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbSellerDao tbSellerDao;


    /**
     * 查询全部
     */
    @Override
    public List<TbSeller> findAll() {
        return tbSellerDao.queryAll(null);
    }


    /**
     * 无条件分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbSeller> list = tbSellerDao.queryAll(null);
        PageInfo<TbSeller> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbSeller seller, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbSeller> list = tbSellerDao.queryAll(seller);
        PageInfo<TbSeller> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加，商家入驻申请
     */
    @Override
    public void add(TbSeller seller) {
        // 新入驻的商家，默认状态为 0，表示未审核
        seller.setStatus("0");
        // 设置入驻申请的提交时间
        seller.setCreateTime(new Date());

        tbSellerDao.insert(seller);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbSeller seller) {
        tbSellerDao.update(seller);
    }


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbSeller findOne(long id) {
        return tbSellerDao.queryById(id + "");
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbSellerDao.deleteById(id + "");
        }
    }

}
