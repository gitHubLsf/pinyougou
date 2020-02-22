package com.lsf.pinyougou.pojogroup;

import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojo.TbGoodsDesc;
import com.lsf.pinyougou.pojo.TbItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 商品组合类
 *
 * @author linshaofeng
 * @date 2020/2/18 15:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Goods implements Serializable {

    /**
     * 商品 SPU
     */
    private TbGoods tbGoods;

    /**
     * 商品详情
     *
     */
    private TbGoodsDesc tbGoodsDesc;

    /**
     * 商品 SKU 列表
     */
    private List<TbItem> tbItemList;
}
