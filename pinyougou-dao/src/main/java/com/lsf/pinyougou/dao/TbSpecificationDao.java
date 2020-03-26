package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbSpecification;

import java.util.List;
import java.util.Map;


public interface TbSpecificationDao {


    TbSpecification queryById(Long id);


    List<TbSpecification> queryAll(TbSpecification tbSpecification);


    int insert(TbSpecification tbSpecification);


    int update(TbSpecification tbSpecification);


    int deleteById(Long id);


    /**
     * 多条件分页查询规格数据
     *
     * @param specification
     * @return
     */
    List<TbSpecification> queryAllLimit(TbSpecification specification);


    /**
     * 查询所有规格下拉列表
     *
     * @return
     */
    List<Map> selectSpecList();

}