package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbGoodsDesc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbGoodsDesc)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:45:42
 */
public interface TbGoodsDescDao {

    /**
     * 通过ID查询单条数据
     *
     * @param goodsId 主键
     * @return 实例对象
     */
    TbGoodsDesc queryById(Long goodsId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbGoodsDesc> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbGoodsDesc 实例对象
     * @return 对象列表
     */
    List<TbGoodsDesc> queryAll(TbGoodsDesc tbGoodsDesc);

    /**
     * 新增数据
     *
     * @param tbGoodsDesc 实例对象
     * @return 影响行数
     */
    int insert(TbGoodsDesc tbGoodsDesc);

    /**
     * 修改数据
     *
     * @param tbGoodsDesc 实例对象
     * @return 影响行数
     */
    int update(TbGoodsDesc tbGoodsDesc);

    /**
     * 通过主键删除数据
     *
     * @param goodsId 主键
     * @return 影响行数
     */
    int deleteById(Long goodsId);

}