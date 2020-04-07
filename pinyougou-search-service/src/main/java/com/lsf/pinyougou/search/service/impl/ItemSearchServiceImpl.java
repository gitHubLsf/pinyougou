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
 * timeout 服务的最长执行时间（单位 ms），如果超过时间还不能执行完，就返回服务超时提示
 * 这个配置也可以写在服务消费方的 @Reference(timeout=5000) 中，但是一般写在服务提供方
 * 如果两方都有写，以服务消费方为准
 */
@Service(timeout = 5000)
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

        // 如果没有指定分页条件，默认是从查询结果的 0 行开始取，取 10 条数据
        // 查询 solr
        ScoredPage<TbItem> itemList
                = solrTemplate.queryForPage(query, TbItem.class);

        // 封装查询后的数据
        map.put("rows", itemList.getContent());

        return map;
    }
}
