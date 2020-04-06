package com.lsf.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.Map;


/**
 * 商品搜索服务实现层
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 搜索商品
     *
     * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> search(Map searchMap) {

        // 构造返回体
        Map<String, Object> map = new HashMap<>();

        // 构造搜索条件
        Query query = new SimpleQuery("*:*");
        // keywords 是用户在商品搜索前台搜索框中输入的内容
        // 将 keywords 映射到 item_keywords 复制域
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        // 查询 solr
        ScoredPage<TbItem> itemList
                = solrTemplate.queryForPage(query, TbItem.class);

        // 封装查询后的数据
        map.put("rows", itemList.getContent());

        return map;
    }
}
