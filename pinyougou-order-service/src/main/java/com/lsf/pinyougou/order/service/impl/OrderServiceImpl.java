package com.lsf.pinyougou.order.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbOrderDao;
import com.lsf.pinyougou.pojo.TbOrder;
import vo.PageResult;


/**
 * 订单服务实现层
 */
@Service
public class OrderServiceImpl implements OrderService {

	/**
	 * 查询全部
	 */
	@Override
	public List<TbOrder> findAll() {
		return tbOrderDao.queryAll(null);
	}
	

	/**
	 * 无条件分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbOrder> list = tbOrderDao.queryAll(null);
        PageInfo<TbOrder> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
	}
	
	
	/**
	 * 多条件分页查询
	 */
	@Override
	public PageResult findPageLimit(TbOrder order, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        List<TbOrder> list = tbOrderDao.queryAll(order);
        PageInfo<TbOrder> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
	}
	

	/**
	 * 添加
	 */
	@Override
	public void add(TbOrder order) {
		tbOrderDao.insert(order);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbOrder order){
		tbOrderDao.update(order);
	}	
	
	
	/**
	 * 根据 ID 获取实体
	 */
	@Override
	public TbOrder findOne(long id){
		return tbOrderDao.queryById(id);
	}


	/**
	 * 批量删除
	 */
	@Override
	public void batchDelete(Long[] ids) {
		for(Long id:ids){
			tbOrderDao.deleteById(id);
		}		
	}


	@Autowired
	private TbOrderDao tbOrderDao;

}
