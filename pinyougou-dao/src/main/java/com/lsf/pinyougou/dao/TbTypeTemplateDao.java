package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbTypeTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbTypeTemplate)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:47:13
 */
public interface TbTypeTemplateDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbTypeTemplate queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbTypeTemplate> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbTypeTemplate 实例对象
     * @return 对象列表
     */
    List<TbTypeTemplate> queryAll(TbTypeTemplate tbTypeTemplate);

    /**
     * 新增数据
     *
     * @param tbTypeTemplate 实例对象
     * @return 影响行数
     */
    int insert(TbTypeTemplate tbTypeTemplate);

    /**
     * 修改数据
     *
     * @param tbTypeTemplate 实例对象
     * @return 影响行数
     */
    int update(TbTypeTemplate tbTypeTemplate);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}