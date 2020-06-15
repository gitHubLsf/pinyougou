package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbSeckillGoods;

import java.util.List;

/**
 * 秒杀商品 dao 层
 */
public interface TbSeckillGoodsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbSeckillGoods queryById(Long id);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbSeckillGoods 实例对象
     * @return 对象列表
     */
    List<TbSeckillGoods> queryAll(TbSeckillGoods tbSeckillGoods);

    /**
     * 新增数据
     *
     * @param tbSeckillGoods 实例对象
     * @return 影响行数
     */
    int insert(TbSeckillGoods tbSeckillGoods);

    /**
     * 修改数据
     *
     * @param tbSeckillGoods 实例对象
     * @return 影响行数
     */
    int update(TbSeckillGoods tbSeckillGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


    /**
     * 返回正在参与秒杀的商品
     * 条件：商品审核通过
     * 		剩余库存 > 0
     * 		当前时间 >= 开始时间
     * 		当前时间 <= 截止时间
     */
    List<TbSeckillGoods> findList(TbSeckillGoods tbSeckillGoods);
}