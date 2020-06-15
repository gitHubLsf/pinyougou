package com.lsf.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbPayLog;
import com.lsf.pinyougou.service.interfaces.order.OrderService;
import com.lsf.pinyougou.service.interfaces.pay.WeChatPayService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.Result;

import java.util.Map;


/**
 * 支付控制层
 */
@RestController
@RequestMapping("/pay")
public class PayController {


    /**
     * 创建支付二维码
     */
    @RequestMapping("/createQRCode.do")
    public Map<String, String> createQRCode() {
        // 获取当前在线用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        // 获取 redis 中缓存的支付日志对象
        TbPayLog payLog = orderService.searchPayLogFromRedis(userName);

        // 判断支付日志对象是否存在
        if (payLog != null) {
            // 如果存在，就生成二维码信息并返回给客户端
            return weChatPayService.createQRCode(payLog.getOutTradeNo(), String.valueOf(payLog.getTotalFee()));
        }

        // 如果支付日志对象不存在，就返回 null
        return null;
    }


    /**
     * 查询订单的支付状态
     *
     * @param outTradeNo 支付订单号
     */
    @RequestMapping("/queryPayStatus.do")
    public Result queryPayStatus(String outTradeNo) {
        // 循环调用微信的查询订单 API，根据返回的支付状态做出判断
        int n = 0;

        while (true) {
            try {
                // 间隔 5 秒调用一次
                Thread.sleep(5000);

                Map<String, String> result = weChatPayService.queryPayStatus(outTradeNo);

                if (result == null || "PAYERROR".equals(result.get("trade_state"))) {
                    return new Result(false, "支付失败");
                }

                if ("SUCCESS".equals(result.get("trade_state"))) {
                    // 修改订单状态
                    orderService.updateOrderStatus(outTradeNo, result.get("transaction_id"));

                    return new Result(true, "支付成功");
                }

                n++;
                if (n >= 12) {
                    // 代表 1 分钟后，订单仍然未支付
                    // 提示客户端更新验证码
                    return new Result(false, "timeout");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Reference
    private OrderService orderService;


    /**
     * 微信支付服务
     */
    @Reference
    private WeChatPayService weChatPayService;
}
