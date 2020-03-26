package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbFreightTemplate implements Serializable {

    private static final long serialVersionUID = -59447343681889214L;

    private Long id;

    /**
     * 商家ID
     */
    private String sellerId;

    /**
     * 是否默认   （‘Y’是   'N'否）
     */
    private String isDefault;

    /**
     * 模版名称
     */
    private String name;

    /**
     * 发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
     */
    private String sendTimeType;

    /**
     * 统一价格
     */
    private Double price;

    /**
     * 创建时间
     */
    private Date createTime;

}