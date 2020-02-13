package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbSpecification;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (TbSpecification)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:47:00
 */
public interface TbSpecificationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbSpecification queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbSpecification> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbSpecification 实例对象
     * @return 对象列表
     */
    List<TbSpecification> queryAll(TbSpecification tbSpecification);

    /**
     * 新增数据
     *
     * @param tbSpecification 实例对象
     * @return 影响行数
     */
    int insert(TbSpecification tbSpecification);

    /**
     * 修改数据
     *
     * @param tbSpecification 实例对象
     * @return 影响行数
     */
    int update(TbSpecification tbSpecification);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
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