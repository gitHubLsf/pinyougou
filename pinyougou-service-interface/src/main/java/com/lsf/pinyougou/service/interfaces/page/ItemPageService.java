package com.lsf.pinyougou.service.interfaces.page;

/**
 * 商品详情页服务接口
 */
public interface ItemPageService {

    /**
     * 批量根据商品 SPU ID 生成商品详情页
     */
    void getItemHtml(Long[] ids);


    /**
     * 批量根据商品 SPU ID 删除生成的商品详情静态页
     */
    void deleteItemHtml(Long[] ids);
}
