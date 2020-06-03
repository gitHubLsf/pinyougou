package com.lsf.pinyougou.pay.service;


import java.util.Map;

/**
 * 微信支付服务接口
 */
public interface WeChatPayService {

    /**
     * 创建支付二维码
     *
     * @param outTradeNo    支付订单号
     * @param totalFee  支付金额（单位：分）
     */
    Map<String, String> createQRCode(String outTradeNo, String totalFee);


    /**
     * 查询订单的支付状态
     *
     * @param outTradeNo    支付订单号
     */
    Map<String, String> queryPayStatus(String outTradeNo);

}
