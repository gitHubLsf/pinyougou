package com.lsf.pinyougou.sellergoods.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbTypeTemplateDao;
import com.lsf.pinyougou.pojo.TbTypeTemplate;
import vo.PageResult;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbTypeTemplateDao tbTypeTemplateDao;


    /**
     * 查询全部
     */
    @Override
    public List<TbTypeTemplate> findAll() {
        return tbTypeTemplateDao.queryAll(null);
    }


    /**
     * 无条件分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbTypeTemplate> list = tbTypeTemplateDao.queryAll(null);
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbTypeTemplate> list = tbTypeTemplateDao.queryAllLimit(typeTemplate);
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加
     */
    @Override
    public void add(TbTypeTemplate typeTemplate) {
        tbTypeTemplateDao.insert(typeTemplate);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbTypeTemplate typeTemplate) {
        tbTypeTemplateDao.update(typeTemplate);
    }


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbTypeTemplate findOne(long id) {
        return tbTypeTemplateDao.queryById(id);
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbTypeTemplateDao.deleteById(id);
        }
    }

}
