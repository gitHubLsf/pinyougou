package com.lsf.pinyougou.cart.controller;
import java.util.List;

import com.lsf.pinyougou.order.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbOrder;

import vo.PageResult;
import vo.Result;


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
	 * 无条件分页查询
	 */
	@RequestMapping("/findPage.do")
	public PageResult  findPage(int page,int size){			
		return orderService.findPage(page, size);
	}
	
	
	/**
	 * 多条件分页查询
	 */
	@RequestMapping("/search.do")
	public PageResult findPageLimit(@RequestBody TbOrder order, int page, int size  ){
		return orderService.findPageLimit(order, page, size);		
	}
	
	
	/**
	 * 添加
	 */
	@RequestMapping("/add.do")
	public Result add(@RequestBody TbOrder order){
		try {
			orderService.add(order);
			return new Result(true, "添加成功");
		} catch (Exception e) {
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
