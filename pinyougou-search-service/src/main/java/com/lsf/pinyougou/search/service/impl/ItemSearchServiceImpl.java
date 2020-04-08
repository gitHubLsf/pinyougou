package com.lsf.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
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

        /*
            搜索结果没有做高亮显示

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
        map.put("rows", itemList.getContent());*/


        // 搜索结果高亮显示

        // 构造搜索对象 query
        HighlightQuery query = new SimpleHighlightQuery();

        // 往 query 中添加高亮配置
        HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        // 往 query 中添加查询条件
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        // 获取查询的高亮列表
        HighlightPage<TbItem> page =
                solrTemplate.queryForHighlightPage(query, TbItem.class);

        // 通过 page.getContent() 获取的查询结果集合是原生的，没有任何高亮修饰
        //List<TbItem> content = page.getContent();

        // 获取高亮列表的高亮入口集合
        List<HighlightEntry<TbItem>> highlightBeginList
                = page.getHighlighted();
        // 循环高亮入口集合
        for (HighlightEntry<TbItem> h : highlightBeginList) {
            /*
            // 获取所有的高亮域（对于每个实体，可能设置多个高亮域，我们这里只设置一个高亮域 item_title）
            List<HighlightEntry.Highlight> hh = h.getHighlights();
            // 遍历高亮域集合
            for (HighlightEntry.Highlight hhh : hh) {
                // 获取每个高亮域的高亮结果，对于每个高亮域，我们可能设置多个前缀和后缀，这里我们只设置一个 <em></em>
                List<String> snipplets = hhh.getSnipplets();
                System.out.println(snipplets.get(0));   // ...<em stype=>item_title</em>...
            }*/

            // 获取原实体类
            TbItem item = h.getEntity();
            // 获取高亮域集合
            List<HighlightEntry.Highlight> hh = h.getHighlights();
            if (hh != null && hh.size() > 0) {
                // 高亮域集合存在，且有元素（我们这里只设置一个高亮域 item_title，所以集合的长度为 1）

                // 获取高亮域的高亮结果集合
                // 因为高亮域集合长度为 1，所以直接 get(0) 获取高亮结果集合
                List<String> snipplets = hh.get(0).getSnipplets();

                if (snipplets != null && snipplets.size() > 0) {
                    // 高亮结果集合存在，且有元素（我们只一个前缀和后缀 <em></em>，所以高亮结果集合长度为 1）
                    String reslut = snipplets.get(0);
                    // 设置到原生实体类中
                    item.setTitle(reslut);
                    // 此处修改的实体类，直接会影响到 page.getContent() 中的实体类
                }
            }
        }

        map.put("rows", page.getContent());

        return map;
    }
}
