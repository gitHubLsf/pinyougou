package com.lsf.pinyougou.page.service;

/**
 * 商品详情页服务接口
 */
public interface ItemPageService {

    /**
     * 根据商品 SPU ID 生成商品详情页
     */
    boolean getItemHtml(Long goodsId);

}
