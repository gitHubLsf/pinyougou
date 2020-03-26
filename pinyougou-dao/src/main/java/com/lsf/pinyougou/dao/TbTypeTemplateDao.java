package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbTypeTemplate;


import java.util.List;
import java.util.Map;


public interface TbTypeTemplateDao {


    TbTypeTemplate queryById(Long id);


    List<TbTypeTemplate> queryAll(TbTypeTemplate tbTypeTemplate);


    int insert(TbTypeTemplate tbTypeTemplate);


    int update(TbTypeTemplate tbTypeTemplate);


    int deleteById(Long id);


    /**
     * 多条件分页查询模板数据
     *
     * @param typeTemplate
     * @return
     */
    List<TbTypeTemplate> queryAllLimit(TbTypeTemplate typeTemplate);


    /**
     * 添加商品分类时，查询所有类型模板列表
     *
     * @return
     */
    List<Map> findTypeList();
}