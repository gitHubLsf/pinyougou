package com.lsf.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbSellerDao;
import com.lsf.pinyougou.pojo.TbSeller;
import com.lsf.pinyougou.service.interfaces.sellergoods.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vo.PageResult;

import java.util.Date;
import java.util.List;


/**
 * 商家服务实现层
 */
@Service
public class SellerServiceImpl implements SellerService {

    /**
     * 查询全部
     */
    @Override
    public List<TbSeller> findAll() {
        return tbSellerDao.queryAll(null);
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbSeller seller, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbSeller> list = tbSellerDao.queryAllLimit(seller);
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

        // 密码加密,采用 BCrypt 加密算法
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPassword = bCryptPasswordEncoder.encode(seller.getPassword());
        seller.setPassword(newPassword);

        tbSellerDao.insert(seller);
    }


    /**
     * 修改商家的审核状态
     */
    @Override
    public void update(TbSeller seller) {
        tbSellerDao.update(seller);
    }


    /**
     * 根据 ID 查询某个未审核商家的全部信息
     */
    @Override
    public TbSeller findOne(String id) {
        return tbSellerDao.queryById(id);
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


    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbSellerDao tbSellerDao;
}
