package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbItemCat;
import com.lsf.pinyougou.pojogroup.TbItemCats;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品类目(TbItemCat)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:45:59
 */
public interface TbItemCatDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbItemCats queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbItemCat> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbItemCat 实例对象
     * @return 对象列表
     */
    List<TbItemCat> queryAll(TbItemCat tbItemCat);

    /**
     * 添加商品分类
     *
     * @param tbItemCat 实例对象
     * @return 影响行数
     */
    int insert(TbItemCat tbItemCat);

    /**
     * 修改数据
     *
     * @param tbItemCat 实例对象
     * @return 影响行数
     */
    int update(TbItemCat tbItemCat);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}