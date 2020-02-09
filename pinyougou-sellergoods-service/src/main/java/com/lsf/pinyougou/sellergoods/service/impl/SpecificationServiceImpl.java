package com.lsf.pinyougou.sellergoods.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbSpecificationOptionDao;
import com.lsf.pinyougou.pojo.TbSpecificationOption;
import com.lsf.pinyougou.pojogroup.Specification;
import com.lsf.pinyougou.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbSpecificationDao;
import com.lsf.pinyougou.pojo.TbSpecification;
import vo.PageResult;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbSpecificationDao tbSpecificationDao;

    @Autowired
    private TbSpecificationOptionDao tbSpecificationOptionDao;


    /**
     * 查询全部
     */
    @Override
    public List<TbSpecification> findAll() {
        return tbSpecificationDao.queryAll(null);
    }


    /**
     * 无条件分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbSpecification> list = tbSpecificationDao.queryAll(null);
        PageInfo<TbSpecification> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbSpecification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbSpecification> list = tbSpecificationDao.queryAllLimit(specification);
        PageInfo<TbSpecification> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加规格
     */
    @Override
    public void add(Specification specification) {
        // 添加规格
        TbSpecification tbspecification = specification.getSpecification();
        tbSpecificationDao.insert(tbspecification);
        // 添加规格选项
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for (TbSpecificationOption to : specificationOptionList) {
            to.setSpecId(tbspecification.getId());
            tbSpecificationOptionDao.insert(to);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(TbSpecification specification) {
        tbSpecificationDao.update(specification);
    }


    /**
     * 根据 ID 获取某种规格的信息
     *
     * @param id
     * @return
     */
    @Override
    public Specification findOne(long id) {
        // 查询规格
        TbSpecification tbSpecification = tbSpecificationDao.queryById(id);
        // 查询规格选项
        List<TbSpecificationOption> tbSpecificationOptionsList = tbSpecificationOptionDao.queryBySpecId(id);
        // 返回规格组合类实体
        return new Specification(tbSpecification, tbSpecificationOptionsList);
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbSpecificationDao.deleteById(id);
        }
    }

}
