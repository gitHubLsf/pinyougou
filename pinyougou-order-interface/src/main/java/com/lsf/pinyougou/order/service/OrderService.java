package com.lsf.pinyougou.order.service;

import java.util.List;
import com.lsf.pinyougou.pojo.TbOrder;
import vo.PageResult;


/**
 * 订单服务层接口
 */
public interface OrderService {

	/**
	 * 返回全部列表
	 */
	List<TbOrder> findAll();
	
	
	/**
	 * 无条件分查询
	 */
	PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 多条件分页查询
	 
	 * @param pageNum 当前页
	 * @param pageSize 每页记录数
	 */
	PageResult findPageLimit(TbOrder order, int pageNum, int pageSize);
	
	
	/**
	 * 添加
	*/
	void add(TbOrder order);
	
	
	/**
	 * 修改
	 */
	void update(TbOrder order);
	

	/**
	 * 根据 ID 获取实体
	 */
	TbOrder findOne(long id);
	
	
	/**
	 * 批量删除
	 */
	void batchDelete(Long[] ids);
	
}
