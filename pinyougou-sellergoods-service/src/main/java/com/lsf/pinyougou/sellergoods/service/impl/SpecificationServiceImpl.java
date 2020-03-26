package com.lsf.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

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
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
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
     * 修改规格
     */
    @Override
    @Transactional
    public void update(Specification specification) {
        // 修改规格名称
        tbSpecificationDao.update(specification.getSpecification());

        // 删除旧的规格选项
        tbSpecificationOptionDao.deleteBySpecId(specification.getSpecification().getId());
        // 添加新的规格选项
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for (TbSpecificationOption to : specificationOptionList) {
            to.setSpecId(specification.getSpecification().getId());
            tbSpecificationOptionDao.insert(to);
        }
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
    @Transactional
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            // 删除某种规格
            tbSpecificationDao.deleteById(id);
            // 删除某种规格对应的规格选项
            tbSpecificationOptionDao.deleteBySpecId(id);
        }
    }


    /**
     * 查询所有规格下拉列表
     *
     * @return
     */
    @Override
    public List<Map> selectSpecList() {
        return tbSpecificationDao.selectSpecList();
    }

}
