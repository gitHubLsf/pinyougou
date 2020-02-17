package com.lsf.pinyougou.sellergoods.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.pojogroup.TbItemCats;
import com.lsf.pinyougou.sellergoods.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbItemCatDao;
import com.lsf.pinyougou.pojo.TbItemCat;
import vo.PageResult;


/**
 * 商品分类服务实现层
 *
 * @author Administrator
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbItemCatDao tbItemCatDao;


    /**
     * 查询全部
     */
    @Override
    public List<TbItemCat> findAll() {
        return tbItemCatDao.queryAll(null);
    }


    /**
     * 无条件分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbItemCat> list = tbItemCatDao.queryAll(null);
        PageInfo<TbItemCat> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbItemCat itemCat, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbItemCat> list = tbItemCatDao.queryAll(itemCat);
        PageInfo<TbItemCat> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加商品分类
     */
    @Override
    public void add(TbItemCat itemCat) {
        tbItemCatDao.insert(itemCat);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbItemCat itemCat) {
        tbItemCatDao.update(itemCat);
    }


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbItemCats findOne(long id) {
        return tbItemCatDao.queryById(id);
    }


    /**
     * 批量删除商品分类
     */
    @Override
    public void batchDelete(Long[] ids) throws Exception {
        for (Long id : ids) {
            // 判断分类 ID 下是否还有分类，有的话不能删除
            TbItemCat tbItemCat = new TbItemCat();
            tbItemCat.setParentId(id);
            List<TbItemCat> tbItemCats = tbItemCatDao.queryAll(tbItemCat);
            if (tbItemCats != null && tbItemCats.size() > 0) {
                throw new Exception();
            } else {
                tbItemCatDao.deleteById(id);
            }
        }
    }


    /**
     * 根据上级 ID 查询商品分类
     *
     * @param parentId
     * @return
     */
    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
        TbItemCat tbItemCat = new TbItemCat();
        tbItemCat.setParentId(parentId);
        return tbItemCatDao.queryAll(tbItemCat);
    }

}
