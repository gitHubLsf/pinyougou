package com.lsf.pinyougou.sellergoods.service;

import java.util.List;

import com.lsf.pinyougou.pojo.TbSpecification;
import vo.PageResult;


/**
 * 服务层接口
 */
public interface SpecificationService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbSpecification> findAll();


    /**
     * 无条件分查询
     *
     * @return
     */
    PageResult findPage(int pageNum, int pageSize);


    /**
     * 多条件分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页记录数
     * @return
     */
    PageResult findPageLimit(TbSpecification specification, int pageNum, int pageSize);


    /**
     * 添加
     */
    void add(TbSpecification specification);


    /**
     * 修改
     */
    void update(TbSpecification specification);


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    TbSpecification findOne(long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids);

}
