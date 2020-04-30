//package com.lsf.pinyougou.sellergoods.service.impl;
//
//import java.util.List;
//
//import com.github.pagehelper.PageInfo;
//import com.lsf.pinyougou.sellergoods.service.SpecificationOptionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.alibaba.dubbo.config.annotation.Service;
//import com.github.pagehelper.PageHelper;
//import com.lsf.pinyougou.dao.TbSpecificationOptionDao;
//import com.lsf.pinyougou.pojo.TbSpecificationOption;
//import vo.PageResult;
//
//
///**
// * 规格选项服务实现层
// */
//@Service
//public class SpecificationOptionServiceImpl implements SpecificationOptionService {
//
//    /**
//     * 查询全部
//     */
//    @Override
//    public List<TbSpecificationOption> findAll() {
//        return tbSpecificationOptionDao.queryAll(null);
//    }
//
//
//    /**
//     * 无条件分页查询
//     */
//    @Override
//    public PageResult findPage(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<TbSpecificationOption> list = tbSpecificationOptionDao.queryAll(null);
//        PageInfo<TbSpecificationOption> pageInfo = new PageInfo<>(list);
//        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
//    }
//
//
//    /**
//     * 多条件分页查询
//     */
//    @Override
//    public PageResult findPageLimit(TbSpecificationOption specificationOption, int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<TbSpecificationOption> list = tbSpecificationOptionDao.queryAll(specificationOption);
//        PageInfo<TbSpecificationOption> pageInfo = new PageInfo<>(list);
//        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
//    }
//
//
//    /**
//     * 添加
//     */
//    @Override
//    public void add(TbSpecificationOption specificationOption) {
//        tbSpecificationOptionDao.insert(specificationOption);
//    }
//
//
//    /**
//     * 修改
//     */
//    @Override
//    public void update(TbSpecificationOption specificationOption) {
//        tbSpecificationOptionDao.update(specificationOption);
//    }
//
//
//    /**
//     * 根据 ID 获取实体
//     */
//    @Override
//    public TbSpecificationOption findOne(long id) {
//        return tbSpecificationOptionDao.queryById(id);
//    }
//
//
//    /**
//     * 批量删除
//     */
//    @Override
//    public void batchDelete(Long[] ids) {
//        for (Long id : ids) {
//            tbSpecificationOptionDao.deleteById(id);
//        }
//    }
//
//
//    /**
//     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
//     */
//    @Autowired
//    private TbSpecificationOptionDao tbSpecificationOptionDao;
//
//}
