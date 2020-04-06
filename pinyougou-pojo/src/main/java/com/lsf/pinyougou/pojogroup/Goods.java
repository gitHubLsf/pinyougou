package com.lsf.pinyougou.pojogroup;


import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojo.TbGoodsDesc;
import com.lsf.pinyougou.pojo.TbItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品组合类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable {

    /**
     * 商品 SPU
     */
    private TbGoods tbGoods;

    /**
     * 商品详情
     */
    private TbGoodsDesc tbGoodsDesc;

    /**
     * 商品 SKU 列表
     */
    private List<TbItem> tbItemList;
}
