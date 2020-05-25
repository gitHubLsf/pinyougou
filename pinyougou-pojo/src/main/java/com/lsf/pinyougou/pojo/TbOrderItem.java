package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 订单详情对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbOrderItem implements Serializable {

    private static final long serialVersionUID = 864255099000887088L;


    private Long id;


    /**
     * 商品 SKU ID
     */
    private Long itemId;


    /**
     * 商品 SPU ID
     */
    private Long goodsId;


    /**
     * 订单 ID
     */
    private Long orderId;


    /**
     * 商家 ID
     */
    private String sellerId;


    /**
     * 商品标题
     */
    private String title;


    /**
     * 商品单价
     */
    private Double price;


    /**
     * 商品购买数量
     */
    private Integer num;


    /**
     * 商品总金额
     */
    private Double totalFee;


    /**
     * 商品图片地址
     */
    private String picPath;
}