package com.lsf.pinyougou.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 商品 SKU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbItem implements Serializable {

    private static final long serialVersionUID = 364891714531212448L;


    /**
     * 商品 SKU ID
     */
    @Field
    private Long id;


    /**
     * 商品标题
     */
    @Field("item_title")
    private String title;


    /**
     * 商品价格
     */
    @Field("item_price")
    private Double price;


    /**
     * 商品图片地址
     */
    @Field("item_image")
    private String image;


    /**
     * 商品 SPU ID
     */
    @Field("item_goodsid")
    private Long goodsId;


    /**
     * 商品分类
     */
    @Field("item_category")
    private String category;


    /**
     * 品牌
     */
    @Field("item_brand")
    private String brand;


    /**
     * 商家名称
     */
    @Field("item_seller")
    private String seller;


    /**
     * 商品卖点
     */
    private String sellPoint;


    /**
     * 库存数量
     */
    private Integer num;



    /**
     * 商品条形码
     */
    private String barcode;


    /**
     * 所属类目，叶子类目
     */
    private Long categoryid;


    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
    private String status;


    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 商品的更新时间
     */
    @Field("item_updateTime")
    private Date updateTime;


    private String itemSn;


    private Integer stockCount;


    private Double costPirce;


    private Double marketPrice;


    private String isDefault;


    private String sellerId;


    private String cartThumbnail;


    private String spec;

    @Dynamic
    @Field("item_spec_*")
    private Map<String, String> specMap;

}