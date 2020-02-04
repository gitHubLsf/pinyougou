package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbAddress)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:44:15
 */
public interface TbAddressDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbAddress queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbAddress> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbAddress 实例对象
     * @return 对象列表
     */
    List<TbAddress> queryAll(TbAddress tbAddress);

    /**
     * 新增数据
     *
     * @param tbAddress 实例对象
     * @return 影响行数
     */
    int insert(TbAddress tbAddress);

    /**
     * 修改数据
     *
     * @param tbAddress 实例对象
     * @return 影响行数
     */
    int update(TbAddress tbAddress);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}