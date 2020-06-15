package com.lsf.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lsf.pinyougou.service.interfaces.pay.WeChatPayService;

import java.util.HashMap;
import java.util.Map;


/**
 * 微信支付服务实现
 */
@Service
public class WeChatPayServiceImpl implements WeChatPayService {

    /**
     * 创建支付二维码
     *
     * @param outTradeNo 支付订单号
     * @param totalFee   支付金额（单位：分）
     */
    @Override
    public Map<String, String> createQRCode(String outTradeNo, String totalFee) {
        // 1.创建请求微信扫码支付接口的参数
//        Map<String, String> param = new HashMap<>();
//        param.put("appid", appId);  // 公众号 ID
//        param.put("mch_id", partner);   // 商家账户
//        param.put("nonce_str", WXPayUtil.generateNonceStr());   // 随机字符串
//        param.put("body", "品优购");   // 商品描述
//        param.put("out_trade_no", outTradeNo); // 支付订单号
//        param.put("total_fee", totalFee);  // 总金额（单位：分）
//        param.put("spbill_create_ip", "127.0.0.1"); // IP
//        param.put("notify_url", notifyUrl);   //回调地址 (随便写)
//        param.put("trade_type", "NATIVE");  // 交易类型

        try {
            // 2.生成要发送的 xml
//            String xmlParam = WXPayUtil.generateSignedXml(param, partnerKey);
//            System.out.println("xmlParam: " + xmlParam);

            // 3.向微信扫码支付接口发送请求
//            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            client.setHttps(true);
//            client.setXmlParam(xmlParam);
//            client.post();

            // 3.获得结果 xml
//            String result = client.getContent();
//            System.out.println("result: " + result);

            // 4.将获得结果 xml 转成 map
            //Map<String, String> resultMap = WXPayUtil.xmlToMap(result);

            // 5.构造自定义返回体
            Map<String, String> map = new HashMap<>();
            //map.put("codeUrl", resultMap.get("code_url")); // 二维码地址
            map.put("codeUrl", "http://www.baidu.com"); // 模拟二维码地址
            map.put("totalFee", totalFee);    // 总金额（单位：分）
            map.put("outTradeNo", outTradeNo);  // 支付订单号

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 查询订单的支付状态
     *
     * @param outTradeNo 支付订单号
     */
    @Override
    public Map<String, String> queryPayStatus(String outTradeNo) {
        // 1.创建请求微信扫码支付，查询订单接口的参数
//        Map<String, String> param = new HashMap<>();
//        param.put("appid", appId);  // 公众号 ID
//        param.put("mch_id", partner);   // 商家账户
//        param.put("out_trade_no", outTradeNo);  // 支付订单号
//        param.put("nonce_str", WXPayUtil.generateNonceStr());   // 随机字符串

        try {
            // 2.将参数转换为 xml
            //String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);

            // 3.发送请求
//            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
//            client.setHttps(true);
//            client.setXmlParam(xmlParam);
//            client.post();

            // 4.获取结果
            //String result = client.getContent();

            // 5.将结果 xml 转换为 map
            //Map<String, String> map = WXPayUtil.xmlToMap(result);
            Map<String, String> map = new HashMap<>();  // 模拟返回的结果

            // 支付成功
            // 设置支付状态为 SUCCESS
            map.put("trade_state", "SUCCESS");

            // 设置交易流水号，
            map.put("transaction_id", String.valueOf(1001));

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    /**
//     * 公众号 ID
//     */
//    @Value("${appId}")
//    private String appId;


//    /**
//     * 商家账户
//     */
//    @Value("${partner}")
//    private String partner;


//    /**
//     * 商家账户密钥
//     */
//    @Value("${partnerKey}")
//    private String partnerKey;


//    /**
//     * 回调地址
//     */
//    @Value("${notifyUrl}")
//    private String notifyUrl;

}
