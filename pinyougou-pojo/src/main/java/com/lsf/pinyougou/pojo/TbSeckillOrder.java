package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (TbSeckillOrder)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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