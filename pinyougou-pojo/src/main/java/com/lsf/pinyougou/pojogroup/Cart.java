package com.lsf.pinyougou.pojogroup;

import com.lsf.pinyougou.pojo.TbOrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 购物车对象：对每个商家的购物车进行的封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {

    /**
     * 商家 ID
     */
    private String sellerId;


    /**
     * 商家名称
     */
    private String sellerName;


    /**
     * 明细列表
     * 明细列表中保存的对象就是 TbOrderItem 订单详情对象
     */
    private List<TbOrderItem> orderItemList;
}
