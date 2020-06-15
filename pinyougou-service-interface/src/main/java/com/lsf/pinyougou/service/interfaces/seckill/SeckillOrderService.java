package com.lsf.pinyougou.service.interfaces.seckill;

import com.lsf.pinyougou.pojo.TbSeckillOrder;
import vo.PageResult;

import java.util.List;


/**
 * 服务层接口
 */
public interface SeckillOrderService {

	/**
	 * 返回全部列表
	 
	 * @return
	 */
	List<TbSeckillOrder> findAll();


	/**
	 * 多条件分页查询
	 
	 * @param pageNum 当前页
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageResult findPageLimit(TbSeckillOrder seckillOrder, int pageNum, int pageSize);
	
	
	/**
	 * 添加
	*/
	void add(TbSeckillOrder seckillOrder);
	
	
	/**
	 * 修改
	 */
	void update(TbSeckillOrder seckillOrder);
	

	/**
	 * 根据 ID 获取实体
	 
	 * @param id
	 * @return
	 */
	TbSeckillOrder findOne(long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDelete(Long[] ids);
	
}
