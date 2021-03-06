package com.lsf.pinyougou.service.interfaces.sellergoods;

import com.lsf.pinyougou.pojo.TbSpecificationOption;
import vo.PageResult;

import java.util.List;


/**
 * 服务层接口
 */
public interface SpecificationOptionService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbSpecificationOption> findAll();


    /**
     * 多条件分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页记录数
     * @return
     */
    PageResult findPageLimit(TbSpecificationOption specificationOption, int pageNum, int pageSize);


    /**
     * 添加
     */
    void add(TbSpecificationOption specificationOption);


    /**
     * 修改
     */
    void update(TbSpecificationOption specificationOption);


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    TbSpecificationOption findOne(long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids);

}
