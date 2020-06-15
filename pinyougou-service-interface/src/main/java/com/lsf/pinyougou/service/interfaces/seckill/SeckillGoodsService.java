package com.lsf.pinyougou.service.interfaces.seckill;

import com.lsf.pinyougou.pojo.TbSeckillGoods;
import vo.PageResult;

import java.util.List;


/**
 * 秒杀商品服务接口
 */
public interface SeckillGoodsService {

	/**
	 * 返回全部列表
	 */
	List<TbSeckillGoods> findAll();


	/**
	 * 多条件分页查询
	 */
	PageResult findPageLimit(TbSeckillGoods seckillGoods, int pageNum, int pageSize);
	
	
	/**
	 * 添加
	*/
	void add(TbSeckillGoods seckillGoods);
	
	
	/**
	 * 修改
	 */
	void update(TbSeckillGoods seckillGoods);
	

	/**
	 * 根据 ID 获取实体
	 */
	TbSeckillGoods findOne(long id);
	
	
	/**
	 * 批量删除
	 */
	void batchDelete(Long[] ids);


	/**
	 * 返回正在参与秒杀的商品
	 * 条件：商品审核通过
	 * 		剩余库存 > 0
	 * 		当前时间 >= 开始时间
	 * 		当前时间 <= 截止时间
	 */
	List<TbSeckillGoods> findList();
}
