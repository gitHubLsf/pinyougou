package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (TbOrderItem)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbOrderItem implements Serializable {
    private static final long serialVersionUID = 864255099000887088L;
    
    private Long id;
    /**
    * 商品id
    */
    private Long itemId;
    /**
    * SPU_ID
    */
    private Long goodsId;
    /**
    * 订单id
    */
    private Long orderId;
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
    
    private String sellerId;

}