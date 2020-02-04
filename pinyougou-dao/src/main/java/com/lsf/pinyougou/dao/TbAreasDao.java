package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbAreas;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行政区域县区信息表(TbAreas)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:44:43
 */
public interface TbAreasDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbAreas queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbAreas> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbAreas 实例对象
     * @return 对象列表
     */
    List<TbAreas> queryAll(TbAreas tbAreas);

    /**
     * 新增数据
     *
     * @param tbAreas 实例对象
     * @return 影响行数
     */
    int insert(TbAreas tbAreas);

    /**
     * 修改数据
     *
     * @param tbAreas 实例对象
     * @return 影响行数
     */
    int update(TbAreas tbAreas);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}