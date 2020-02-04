package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbCities;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行政区域地州市信息表(TbCities)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:44:59
 */
public interface TbCitiesDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbCities queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbCities> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbCities 实例对象
     * @return 对象列表
     */
    List<TbCities> queryAll(TbCities tbCities);

    /**
     * 新增数据
     *
     * @param tbCities 实例对象
     * @return 影响行数
     */
    int insert(TbCities tbCities);

    /**
     * 修改数据
     *
     * @param tbCities 实例对象
     * @return 影响行数
     */
    int update(TbCities tbCities);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}