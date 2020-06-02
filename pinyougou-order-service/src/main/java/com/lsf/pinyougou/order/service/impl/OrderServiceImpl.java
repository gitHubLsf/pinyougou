package com.lsf.pinyougou.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbOrderItemDao;
import com.lsf.pinyougou.order.service.OrderService;
import com.lsf.pinyougou.pojo.TbOrderItem;
import com.lsf.pinyougou.pojogroup.Cart;
import com.lsf.pinyougou.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbOrderDao;
import com.lsf.pinyougou.pojo.TbOrder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
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
     * 添加订单
     */
    @Override
	@Transactional
    public void add(TbOrder order) {
        // 从缓存中查询购物车列表
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(order.getUserId());

        // 循环每个购物车对象，代表一条订单主表记录
        for (Cart cart : cartList) {
            long orderId = idWorker.nextId();

            TbOrder tbOrder = new TbOrder();
            tbOrder.setOrderId(orderId);
            tbOrder.setUserId(order.getUserId());    // 用户名
            tbOrder.setPaymentType(order.getPaymentType());    // 支付类型
            // 上述要做支付类型的合法性判断

            tbOrder.setStatus("1");    // 状态：未付款
            tbOrder.setCreateTime(new Date());    // 订单创建日期
            tbOrder.setUpdateTime(new Date());    // 订单更新日期
            tbOrder.setReceiverAreaName(order.getReceiverAreaName());    // 收件人地址
            tbOrder.setReceiverMobile(order.getReceiverMobile());    // 收件人手机号
            tbOrder.setReceiver(order.getReceiver());    // 收件人
            tbOrder.setSourceType(order.getSourceType());    // 订单来源
            tbOrder.setSellerId(cart.getSellerId());    // 商家 ID

            // 循环购物车对象的明细列表，每一个明细对象代表一个订单明细表记录
            Double money = 0.0;
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                orderItem.setId(idWorker.nextId());
                orderItem.setOrderId(orderId);    // 所属的订单主表 ID
                orderItem.setSellerId(cart.getSellerId());    // 商家 ID

                money += orderItem.getTotalFee();    // 订单金额累加=各订单明细表记录的总金额累加

				tbOrderItemDao.insert(orderItem);
            }

            // 设置订单主表总金额
            tbOrder.setPayment(money);

            tbOrderDao.insert(tbOrder);
        }

        // 清空缓存中的购物车列表
        redisTemplate.boundHashOps("cartList").delete(order.getUserId());
    }


    /**
     * 修改
     */
    @Override
    public void update(TbOrder order) {
        tbOrderDao.update(order);
    }


    /**
     * 根据 ID 获取实体
     */
    @Override
    public TbOrder findOne(long id) {
        return tbOrderDao.queryById(id);
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbOrderDao.deleteById(id);
        }
    }


    @Autowired
    private TbOrderDao tbOrderDao;


    @Autowired
    private TbOrderItemDao tbOrderItemDao;


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 分布式 ID 生成器
     */
    @Autowired
    private IdWorker idWorker;
}
