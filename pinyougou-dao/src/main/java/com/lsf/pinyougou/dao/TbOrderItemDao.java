package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbOrderItem)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:46:15
 */
public interface TbOrderItemDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbOrderItem queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbOrderItem> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbOrderItem 实例对象
     * @return 对象列表
     */
    List<TbOrderItem> queryAll(TbOrderItem tbOrderItem);

    /**
     * 新增数据
     *
     * @param tbOrderItem 实例对象
     * @return 影响行数
     */
    int insert(TbOrderItem tbOrderItem);

    /**
     * 修改数据
     *
     * @param tbOrderItem 实例对象
     * @return 影响行数
     */
    int update(TbOrderItem tbOrderItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}