package com.lsf.pinyougou.service.interfaces.order;

import com.lsf.pinyougou.pojo.TbOrder;
import com.lsf.pinyougou.pojo.TbPayLog;
import vo.PageResult;

import java.util.List;


/**
 * 订单服务层接口
 */
public interface OrderService {

	/**
	 * 返回全部列表
	 */
	List<TbOrder> findAll();

	
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


	/**
	 * 根据用户 ID 查询 redis 中缓存的支付日志对象
	 *
	 * @param userId	用户 ID
	 */
	TbPayLog searchPayLogFromRedis(String userId);


	/**
	 * 用户支付成功后，修改订单主表和支付日志表的信息
	 *
	 * @param outTradeNo	支付订单号
	 * @param transactionId	微信返回的交易流水号
	 */
	void updateOrderStatus(String outTradeNo, String transactionId);

}
