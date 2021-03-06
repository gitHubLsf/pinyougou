package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;


/**
 * 秒杀订单表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbSeckillOrder implements Serializable {
    private static final long serialVersionUID = 481586497430599838L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 秒杀商品ID
     */
    private Long seckillId;
    /**
     * 支付金额
     */
    private Double money;
    /**
     * 用户
     */
    private String userId;
    /**
     * 商家
     */
    private String sellerId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 收货人地址
     */
    private String receiverAddress;
    /**
     * 收货人电话
     */
    private String receiverMobile;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 交易流水
     */
    private String transactionId;

}