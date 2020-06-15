package com.lsf.pinyougou.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbSeckillOrderDao;
import com.lsf.pinyougou.pojo.TbSeckillOrder;
import com.lsf.pinyougou.service.interfaces.seckill.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import vo.PageResult;

import java.util.List;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

	/**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
	@Autowired
	private TbSeckillOrderDao tbSeckillOrderDao;
	
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillOrder> findAll() {
		return tbSeckillOrderDao.queryAll(null);
	}
	

	/**
	 * 多条件分页查询
	 */
	@Override
	public PageResult findPageLimit(TbSeckillOrder seckillOrder, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        List<TbSeckillOrder> list = tbSeckillOrderDao.queryAll(seckillOrder);
        PageInfo<TbSeckillOrder> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
	}
	

	/**
	 * 添加
	 */
	@Override
	public void add(TbSeckillOrder seckillOrder) {
		tbSeckillOrderDao.insert(seckillOrder);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillOrder seckillOrder){
		tbSeckillOrderDao.update(seckillOrder);
	}	
	
	
	/**
	 * 根据 ID 获取实体
	 
	 * @param id
	 * @return
	 */
	@Override
	public TbSeckillOrder findOne(long id){
		return tbSeckillOrderDao.queryById(id);
	}


	/**
	 * 批量删除
	 */
	@Override
	public void batchDelete(Long[] ids) {
		for(Long id:ids){
			tbSeckillOrderDao.deleteById(id);
		}		
	}
		
}
