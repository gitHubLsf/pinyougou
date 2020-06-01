package com.lsf.pinyougou.user.service;

import java.util.List;

import com.lsf.pinyougou.pojo.TbAddress;
import com.lsf.pinyougou.pojo.TbUser;
import vo.PageResult;


/**
 * 用户服务层接口
 */
public interface UserService {

	/**
	 * 返回全部列表
	 */
	List<TbUser> findAll();
	
	
	/**
	 * 无条件分查询
	 */
	PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 多条件分页查询
	 */
	PageResult findPageLimit(TbUser user, int pageNum, int pageSize);
	
	
	/**
	 * 用户注册
	*/
	void add(TbUser user);
	
	
	/**
	 * 修改
	 */
	void update(TbUser user);
	

	/**
	 * 根据 ID 获取实体
	 */
	TbUser findOne(long id);
	
	
	/**
	 * 批量删除
	 */
	void batchDelete(Long[] ids);


	/**
	 * 为手机号 phone 生成短信验证码
	 */
	void createSmsCode(String phone);


	/**
	 * 判断手机号 phone 对应的验证码是否正确
	 */
	boolean checkSmsCode(String phone, String smsCode);



}
