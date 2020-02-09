package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbSpecificationOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbSpecificationOption)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:47:06
 */
public interface TbSpecificationOptionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbSpecificationOption queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbSpecificationOption> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbSpecificationOption 实例对象
     * @return 对象列表
     */
    List<TbSpecificationOption> queryAll(TbSpecificationOption tbSpecificationOption);

    /**
     * 新增数据
     *
     * @param tbSpecificationOption 实例对象
     * @return 影响行数
     */
    int insert(TbSpecificationOption tbSpecificationOption);

    /**
     * 修改数据
     *
     * @param tbSpecificationOption 实例对象
     * @return 影响行数
     */
    int update(TbSpecificationOption tbSpecificationOption);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


    /**
     * 查询某种规格 ID 包含的全部规格选项
     *
     * @param id
     * @return
     */
    List<TbSpecificationOption> queryBySpecId(Long id);

}