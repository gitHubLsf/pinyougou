package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbGoods)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:45:35
 */
public interface TbGoodsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbGoods queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbGoods> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbGoods 实例对象
     * @return 对象列表
     */
    List<TbGoods> queryAll(TbGoods tbGoods);

    /**
     * 新增数据
     *
     * @param tbGoods 实例对象
     * @return 影响行数
     */
    int insert(TbGoods tbGoods);

    /**
     * 修改数据
     *
     * @param tbGoods 实例对象
     * @return 影响行数
     */
    int update(TbGoods tbGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}