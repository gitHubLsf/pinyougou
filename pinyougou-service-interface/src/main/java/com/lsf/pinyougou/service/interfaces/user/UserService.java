package com.lsf.pinyougou.service.interfaces.user;

import com.lsf.pinyougou.pojo.TbUser;
import vo.PageResult;

import java.util.List;


/**
 * 用户服务层接口
 */
public interface UserService {

	/**
	 * 返回全部列表
	 */
	List<TbUser> findAll();


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
