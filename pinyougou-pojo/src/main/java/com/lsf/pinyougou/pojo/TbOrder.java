package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbOrder implements Serializable {
    private static final long serialVersionUID = 984921735276311416L;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private Double payment;
    /**
     * 支付类型，1、在线支付，2、货到付款
     */
    private String paymentType;
    /**
     * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String postFee;
    /**
     * 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     */
    private String status;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 订单更新时间
     */
    private Date updateTime;
    /**
     * 付款时间
     */
    private Date paymentTime;
    /**
     * 发货时间
     */
    private Date consignTime;
    /**
     * 交易完成时间
     */
    private Date endTime;
    /**
     * 交易关闭时间
     */
    private Date closeTime;
    /**
     * 物流名称
     */
    private String shippingName;
    /**
     * 物流单号
     */
    private String shippingCode;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 买家留言
     */
    private String buyerMessage;
    /**
     * 买家昵称
     */
    private String buyerNick;
    /**
     * 买家是否已经评价
     */
    private String buyerRate;
    /**
     * 收货人地区名称(省，市，县)街道
     */
    private String receiverAreaName;
    /**
     * 收货人手机
     */
    private String receiverMobile;
    /**
     * 收货人邮编
     */
    private String receiverZipCode;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 过期时间，定期清理
     */
    private Date expire;
    /**
     * 发票类型(普通发票，电子发票，增值税发票)
     */
    private String invoiceType;
    /**
     * 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    private String sourceType;
    /**
     * 商家ID
     */
    private String sellerId;

}