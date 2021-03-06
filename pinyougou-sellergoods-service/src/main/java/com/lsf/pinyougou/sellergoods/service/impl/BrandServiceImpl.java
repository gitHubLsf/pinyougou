package com.lsf.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbBrandDao;
import com.lsf.pinyougou.pojo.TbBrand;
import com.lsf.pinyougou.service.interfaces.sellergoods.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import vo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 品牌列表服务实现
 */
@Service
public class BrandServiceImpl implements BrandService {

    /**
     * 查询所有品牌
     */
    @Override
    public List<TbBrand> findAll() {
        return tbBrandDao.queryAll(null);
    }


    /**
     * 多条件品牌分页查询
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
     */
    @Override
    public void add(TbBrand brand) {
        tbBrandDao.insert(brand);
    }


    /**
     * 根据品牌 ID 查询品牌信息
     */
    @Override
    public TbBrand findOne(long id) {
        return tbBrandDao.queryById(id);
    }


    /**
     * 修改品牌信息
     */
    @Override
    public void update(TbBrand brand) {
        tbBrandDao.update(brand);
    }


    /**
     * 批量删除品牌
     */
    @Override
    public void batchDelete(Long[] ids) {
        if (ids != null && ids.length > 0)
            tbBrandDao.batchDeleteBrand(ids);
    }


    /**
     * 查询所有品牌下拉列表
     */
    @Override
    public List<Map> selectBrandList() {
        return tbBrandDao.selectBrandList();
    }


    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbBrandDao tbBrandDao;
}
