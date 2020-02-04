package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbPayLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbPayLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:46:25
 */
public interface TbPayLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param outTradeNo 主键
     * @return 实例对象
     */
    TbPayLog queryById(String outTradeNo);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbPayLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbPayLog 实例对象
     * @return 对象列表
     */
    List<TbPayLog> queryAll(TbPayLog tbPayLog);

    /**
     * 新增数据
     *
     * @param tbPayLog 实例对象
     * @return 影响行数
     */
    int insert(TbPayLog tbPayLog);

    /**
     * 修改数据
     *
     * @param tbPayLog 实例对象
     * @return 影响行数
     */
    int update(TbPayLog tbPayLog);

    /**
     * 通过主键删除数据
     *
     * @param outTradeNo 主键
     * @return 影响行数
     */
    int deleteById(String outTradeNo);

}