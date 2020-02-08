package com.lsf.pinyougou.sellergoods.service;

import java.util.List;

import com.lsf.pinyougou.pojo.TbTypeTemplate;
import vo.PageResult;


/**
 * 服务层接口
 */
public interface TypeTemplateService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbTypeTemplate> findAll();


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
    PageResult findPageLimit(TbTypeTemplate typeTemplate, int pageNum, int pageSize);


    /**
     * 添加
     */
    void add(TbTypeTemplate typeTemplate);


    /**
     * 修改
     */
    void update(TbTypeTemplate typeTemplate);


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    TbTypeTemplate findOne(long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids);

}
