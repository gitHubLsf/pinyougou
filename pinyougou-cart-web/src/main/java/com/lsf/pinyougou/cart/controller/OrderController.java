package com.lsf.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbOrder;
import com.lsf.pinyougou.service.interfaces.order.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;


/**
 * 订单管理控制层
 */
@RestController
@RequestMapping("/order")
public class OrderController {
	
	/**
	 * 返回全部列表
	 */
	@RequestMapping("/findAll.do")
	public List<TbOrder> findAll(){			
		return orderService.findAll();
	}

	
	/**
	 * 多条件分页查询
	 */
	@RequestMapping("/search.do")
	public PageResult findPageLimit(@RequestBody TbOrder order, int page, int size  ){
		return orderService.findPageLimit(order, page, size);		
	}
	
	
	/**
	 * 添加订单
	 */
	@RequestMapping("/add.do")
	public Result add(@RequestBody TbOrder order){
		// 获取当前在线用户
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		order.setUserId(username);
		order.setSourceType("2");	// 订单来源，"2" 代表用户从 PC 端下单
		// 上述要做订单来源的合法性判断

		try {
			orderService.add(order);

			return new Result(true, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}
	
	
	/**
	 * 修改
	 */
	@RequestMapping("/update.do")
	public Result update(@RequestBody TbOrder order){
		try {
			orderService.update(order);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			return new Result(false, "修改失败");
		}
	}	
	
	
	/**
	 * 根据 ID 获取实体
	 */
	@RequestMapping("/findOne.do")
	public TbOrder findOne(long id){
		return orderService.findOne(id);		
	}
	

	/**
	 * 批量删除
	 */
	@RequestMapping("/batchDelete.do")
	public Result batchDelete(Long [] ids){
		try {
			orderService.batchDelete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			return new Result(false, "删除失败");
		}
	}



	@Reference
	private OrderService orderService;

}
