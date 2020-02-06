package com.lsf.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbBrandDao;
import com.lsf.pinyougou.pojo.TbBrand;
import com.lsf.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import vo.PageResult;

import java.util.List;

/**
 * 品牌列表服务实现
 *
 * @author linshaofeng
 * @date 2020/2/4 13:02
 */
@Service
public class BrandServiceImpl implements BrandService {

    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbBrandDao tbBrandDao;


    /**
     * 查询所有品牌
     * @return
     */
    @Override
    public List<TbBrand> findAll() {
        return tbBrandDao.queryAll(null);
    }

    
    /**
     * 品牌分页
     * @param pageNum   当前页码
     * @param pageSize  每页记录数
     * @return
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有数据
        List<TbBrand> list = tbBrandDao.queryAll(null);
        // 获取分页结果
        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);
        // 返回分页结果类对象
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件品牌分页查询
     *
     * @param brand
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPageLimit(TbBrand brand, int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有数据
        List<TbBrand> list = tbBrandDao.queryAllLimit(brand);
        // 获取分页结果
        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);
        // 返回分页结果类对象
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加品牌
     *
     * @param brand
     */
    @Override
    public void add(TbBrand brand) {
        tbBrandDao.insert(brand);
    }


    /**
     * 根据品牌 ID 查询品牌信息
     *
     * @param id
     * @return
     */
    @Override
    public TbBrand findOne(long id) {
        return tbBrandDao.queryById(id);
    }


    /**
     * 修改品牌信息
     * @param brand
     */
    @Override
    public void update(TbBrand brand) {
        tbBrandDao.update(brand);
    }


    /**
     * 批量删除品牌
     *
     * @param ids
     */
    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbBrandDao.deleteById(id);
        }
    }
}
