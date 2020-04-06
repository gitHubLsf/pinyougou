package com.lsf.pinyougou.search.service;

import java.util.Map;

/**
 * 商品搜索服务接口层
 */
public interface ItemSearchService {

    /**
     * 搜索商品
     * @param searchMap
     * @return
     */
    Map<String, Object> search(Map searchMap);

}
