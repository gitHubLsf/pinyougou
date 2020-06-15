package com.lsf.pinyougou.service.interfaces.user;

import com.lsf.pinyougou.pojo.TbAddress;
import vo.PageResult;

import java.util.List;


/**
 * 地址服务层接口
 */
public interface AddressService {

	/**
	 * 返回全部列表
	 */
	List<TbAddress> findAll();


	
	/**
	 * 多条件分页查询
	 
	 * @param pageNum 当前页
	 * @param pageSize 每页记录数
	 */
	PageResult findPageLimit(TbAddress address, int pageNum, int pageSize);
	
	
	/**
	 * 添加
	*/
	void add(TbAddress address);
	
	
	/**
	 * 修改
	 */
	void update(TbAddress address);
	

	/**
	 * 根据 ID 获取实体
	 */
	TbAddress findOne(long id);
	
	
	/**
	 * 批量删除
	 */
	void batchDelete(Long[] ids);


	/**
	 * 根据用户 ID 查询用户的所有地址
	 */
	List<TbAddress> findAddressListByUserId(String userId);
	
}
