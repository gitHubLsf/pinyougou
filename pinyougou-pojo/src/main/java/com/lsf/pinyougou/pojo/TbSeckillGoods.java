package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * 秒杀商品表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbSeckillGoods implements Serializable {
    private static final long serialVersionUID = -71047823890522006L;
    
    private Long id;

    /**
    * spu ID
    */
    private Long goodsId;

    /**
    * sku ID
    */
    private Long itemId;

    /**
    * 标题
    */
    private String title;

    /**
    * 商品图片
    */
    private String smallPic;

    /**
    * 原价格
    */
    private Double price;

    /**
    * 秒杀价格
    */
    private Double costPrice;

    /**
    * 商家ID
    */
    private String sellerId;

    /**
    * 添加日期
    */
    private Date createTime;

    /**
    * 审核日期
    */
    private Date checkTime;

    /**
    * 审核状态
    */

    private String status;

    /**
    * 开始时间
    */
    private Date startTime;

    /**
    * 结束时间
    */
    private Date endTime;

    /**
    * 秒杀商品数
    */
    private Integer num;

    /**
    * 剩余库存数
    */
    private Integer stockCount;

    /**
    * 描述
    */
    private String introduction;
}