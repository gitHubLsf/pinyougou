package com.lsf.pinyougou.service.interfaces.search;

import com.lsf.pinyougou.pojo.TbItem;

import java.util.List;
import java.util.Map;


/**
 * 商品搜索服务接口层
 */
public interface ItemSearchService {

    /**
     * 搜索商品
     * @param searchMap
     */
    Map<String, Object> search(Map searchMap);


    /**
     * 批量导入商品 SKU 到 solr 中
     */
    void batchImportItem(List<TbItem> itemList);


    /**
     * 根据 item_goodsid 批量删除 solr 中的商品
     */
    void batchDeleteItem(List<Long> ids);
}
