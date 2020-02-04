package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (TbPayLog)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbPayLog implements Serializable {
    private static final long serialVersionUID = -50677486848134698L;
    /**
    * 支付订单号
    */
    private String outTradeNo;
    /**
    * 创建日期
    */
    private Date createTime;
    /**
    * 支付完成时间
    */
    private Date payTime;
    /**
    * 支付金额（分）
    */
    private Long totalFee;
    /**
    * 用户ID
    */
    private String userId;
    /**
    * 交易号码
    */
    private String transactionId;
    /**
    * 交易状态
    */
    private String tradeState;
    /**
    * 订单编号列表
    */
    private String orderList;
    /**
    * 支付类型
    */
    private String payType;

}