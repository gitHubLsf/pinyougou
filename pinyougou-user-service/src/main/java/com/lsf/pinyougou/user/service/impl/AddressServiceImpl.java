package com.lsf.pinyougou.user.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbAddressDao;
import com.lsf.pinyougou.pojo.TbAddress;
import com.lsf.pinyougou.user.service.AddressService;
import vo.PageResult;


/**
 * 地址服务实现层
 */
@Service
public class AddressServiceImpl implements AddressService {

	/**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
	@Autowired
	private TbAddressDao tbAddressDao;
	
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbAddress> findAll() {
		return tbAddressDao.queryAll(null);
	}
	

	/**
	 * 无条件分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbAddress> list = tbAddressDao.queryAll(null);
        PageInfo<TbAddress> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
	}
	
	
	/**
	 * 多条件分页查询
	 */
	@Override
	public PageResult findPageLimit(TbAddress address, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        List<TbAddress> list = tbAddressDao.queryAll(address);
        PageInfo<TbAddress> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
	}
	

	/**
	 * 添加地址
	 */
	@Override
	public void add(TbAddress address) {
		tbAddressDao.insert(address);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbAddress address){
		tbAddressDao.update(address);
	}	
	
	
	/**
	 * 根据 ID 获取实体
	 */
	@Override
	public TbAddress findOne(long id){
		return tbAddressDao.queryById(id);
	}


	/**
	 * 批量删除
	 */
	@Override
	public void batchDelete(Long[] ids) {
		for(Long id:ids){
			tbAddressDao.deleteById(id);
		}		
	}


	/**
	 * 根据用户 ID 查询用户的所有地址
	 */
	@Override
	public List<TbAddress> findAddressListByUserId(String userId) {
		TbAddress tbAddress = new TbAddress();
		tbAddress.setUserId(userId);
		return tbAddressDao.queryAll(tbAddress);
	}
		
}
