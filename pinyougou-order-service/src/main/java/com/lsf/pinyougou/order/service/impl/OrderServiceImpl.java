package com.lsf.pinyougou.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbOrderItemDao;
import com.lsf.pinyougou.dao.TbPayLogDao;
import com.lsf.pinyougou.order.service.OrderService;
import com.lsf.pinyougou.pojo.TbOrderItem;
import com.lsf.pinyougou.pojo.TbPayLog;
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

        List<Long> orderIdList = new ArrayList<>(); // 保存所有的订单号
        Double totalFee = 0.0;      // 计算所有订单的总金额

        // 循环每个购物车对象，代表一条订单主表记录
        for (Cart cart : cartList) {
            // 订单号
            long orderId = idWorker.nextId();
            orderIdList.add(orderId);

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

            // 累计全部订单主表的总金额
            totalFee += money;

            tbOrderDao.insert(tbOrder);
        }


        // 添加支付日志
        // 如果是在线支付
        if ("1".equals(order.getPaymentType())) {
            TbPayLog payLog = new TbPayLog();

            // 支付订单号
            payLog.setOutTradeNo(String.valueOf(idWorker.nextId()));

            // 创建时间
            payLog.setCreateTime(new Date());

            // 将订单号集合 orderIdList 转成 1,2,3,... 的形式
            // 保存订单号集合的作用是，当用户支付完成后，可以将对应的订单的状态修改为已支付
            String orderIdListString = orderIdList
                    .toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replaceAll(" ", "");
            payLog.setOrderList(orderIdListString);

            // 支付类型
            payLog.setPayType("1");

            // 支付总金额，单位为分
            payLog.setTotalFee((long) (totalFee * 100));

            // 支付状态，暂时为未支付
            payLog.setTradeState("0");

            // 用户ID
            payLog.setUserId(order.getUserId());

            // 插入到支付日志表
            tbPayLogDao.insert(payLog);

            // 放入缓存，方便后期生成二维码时使用
            redisTemplate.boundHashOps("payLog").put(order.getUserId(), payLog);
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


    /**
     * 根据用户 ID 查询 redis 中缓存的支付日志对象
     *
     * @param userId 用户 ID
     */
    @Override
    public TbPayLog searchPayLogFromRedis(String userId) {
        return (TbPayLog) redisTemplate.boundHashOps("payLog").get(userId);
    }


    /**
     * 用户支付成功后，修改订单主表和支付日志表的信息
     *
     * @param outTradeNo    支付订单号
     * @param transactionId 微信返回的交易流水号
     */
    @Override
    @Transactional
    public void updateOrderStatus(String outTradeNo, String transactionId) {
        // 1.修改支付日志表的信息
        TbPayLog payLog = new TbPayLog();
        payLog.setOutTradeNo(outTradeNo);

        // 设置支付完成的时间
        payLog.setPayTime(new Date());

        // 修改支付状态为 已支付
        payLog.setTradeState("1");

        // 添加微信返回的交易流水号
        payLog.setTransactionId(transactionId);

        tbPayLogDao.update(payLog);

        // 2.修改订单主表的信息
        // 查询支付日志表中支付对象
        TbPayLog tbPayLog = tbPayLogDao.queryById(outTradeNo);

        // 获取订单号列表
        String[] orderIdsString = tbPayLog.getOrderList().split(",");
        int index = 0;
        Long[] orderIds = new Long[orderIdsString.length];
        for (String orderId : orderIdsString) {
            orderIds[index++] = Long.parseLong(orderId);
        }

        // 批量修改订单主表中的状态，设置为 "2" 代表已付款
        tbOrderDao.batchUpdateStatusByOrderId(orderIds, "2");

        // 3.清除 redis 中缓存的支付日志对象
        redisTemplate.boundHashOps("payLog").delete(tbPayLog.getUserId());
    }


    @Autowired
    private TbOrderDao tbOrderDao;


    @Autowired
    private TbOrderItemDao tbOrderItemDao;


    /**
     * 支付日志表
     */
    @Autowired
    private TbPayLogDao tbPayLogDao;


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 分布式 ID 生成器
     */
    @Autowired
    private IdWorker idWorker;
}
