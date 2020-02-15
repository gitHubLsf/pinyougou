package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbSeller;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbSeller)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 17:46:51
 */
public interface TbSellerDao {

    /**
     * 通过 ID 查询某个未审核商家的全部信息
     *
     * @param sellerId 主键
     * @return 实例对象
     */
    TbSeller queryById(String sellerId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbSeller> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbSeller 实例对象
     * @return 对象列表
     */
    List<TbSeller> queryAll(TbSeller tbSeller);

    /**
     * 新增数据
     *
     * @param tbSeller 实例对象
     * @return 影响行数
     */
    int insert(TbSeller tbSeller);

    /**
     * 修改商家的审核状态
     *
     * @param tbSeller 实例对象
     * @return 影响行数
     */
    int update(TbSeller tbSeller);

    /**
     * 通过主键删除数据
     *
     * @param sellerId 主键
     * @return 影响行数
     */
    int deleteById(String sellerId);


    /**
     * 多条件分页查询未审核商家列表
     *
     * @param seller
     * @return
     */
    List<TbSeller> queryAllLimit(TbSeller seller);
}