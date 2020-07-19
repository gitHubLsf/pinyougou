package com.lsf.pinyougou.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbSeckillGoodsDao;
import com.lsf.pinyougou.pojo.TbSeckillGoods;
import com.lsf.pinyougou.service.interfaces.seckill.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import vo.PageResult;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * 秒杀商品服务层
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillGoods> findAll() {
		return tbSeckillGoodsDao.queryAll(null);
	}
	

	/**
	 * 多条件分页查询
	 */
	@Override
	public PageResult findPageLimit(TbSeckillGoods seckillGoods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        List<TbSeckillGoods> list = tbSeckillGoodsDao.queryAll(seckillGoods);
        PageInfo<TbSeckillGoods> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
	}
	

	/**
	 * 添加
	 */
	@Override
	public void add(TbSeckillGoods seckillGoods) {
		tbSeckillGoodsDao.insert(seckillGoods);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillGoods seckillGoods){
		tbSeckillGoodsDao.update(seckillGoods);
	}	
	
	
	/**
	 * 根据 ID 获取实体
	 */
	@Override
	public TbSeckillGoods findOne(long id){
		return tbSeckillGoodsDao.queryById(id);
	}


	/**
	 * 批量删除
	 */
	@Override
	public void batchDelete(Long[] ids) {
		for(Long id:ids){
			tbSeckillGoodsDao.deleteById(id);
		}		
	}


	/**
	 * 当服务启动后，主动往缓存中填入商品
	 */
	@PostConstruct
	public void preCache() {
		TbSeckillGoods tbSeckillGoods = new TbSeckillGoods();
		tbSeckillGoods.setStatus("1");	// 商品审核通过

		// 剩余库存 > 0
		// 前时间 >= 开始时间
		// 当前时间 <= 截止时间
		// 以上三个条件在 sql 语句中设置
		List<TbSeckillGoods> list = tbSeckillGoodsDao.findList(tbSeckillGoods);

		// 遍历数据库查询结果，将其放入缓存
		for (TbSeckillGoods seckillGoods : list) {
			redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId(), seckillGoods);
		}
	}


	/**
	 * 返回正在参与秒杀的商品
	 * 条件：商品审核通过
	 * 		剩余库存 > 0
	 * 		当前时间 >= 开始时间
	 * 		当前时间 <= 截止时间
	 */
	@Override
	public List<TbSeckillGoods> findList() {
		// 先尝试从缓存中读取数据
		// 缓存采用 hash 表，对所有的秒杀商品进行缓存，key 为秒杀商品的 ID ，value 为秒杀商品对象
		// 将 seckillGoods hash 表的所有 value 都取出来，也就是所有秒杀商品对象
		List<TbSeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckillGoods").values();

		if (seckillGoodsList == null || seckillGoodsList.size() == 0) {
			// 缓存中没有，就从数据库查询
			TbSeckillGoods tbSeckillGoods = new TbSeckillGoods();
			tbSeckillGoods.setStatus("1");	// 商品审核通过

			// 剩余库存 > 0
			// 前时间 >= 开始时间
			// 当前时间 <= 截止时间
			// 以上三个条件在 sql 语句中设置
			seckillGoodsList = tbSeckillGoodsDao.findList(tbSeckillGoods);

			// 遍历数据库查询结果，将其放入缓存
			for (TbSeckillGoods seckillGoods : seckillGoodsList) {
				redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId(), seckillGoods);
			}
		}

		return seckillGoodsList;
	}


	/**
	 * 从缓存中查询某个参与秒杀的商品
	 *
	 * @param id	秒杀商品 ID
	 */
	public TbSeckillGoods findOneFromCache(Long id) {
		// 因为已经预先缓存了所有参与秒杀的商品，所以此处直接从缓存中查询
		return (TbSeckillGoods)redisTemplate.boundHashOps("seckillGoods").get(id);
	}


	@Autowired
	private TbSeckillGoodsDao tbSeckillGoodsDao;


	@Autowired
	private RedisTemplate redisTemplate;
}
