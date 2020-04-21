package com.lsf.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
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


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 搜索商品
     *
     * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> search(Map searchMap) {

        // 构造返回体
        Map<String, Object> maps = new HashMap<>();

        // 去掉搜索添加 searchMap 中 keywords 关键字的空格
        String keywords = (String) searchMap.get("keywords");
        if (keywords == null) {
            return maps;
        }
        keywords = keywords.replace(" ", "");
        if ("".equals(keywords)) {
            return maps;
        }
        searchMap.put("keywords", keywords);

        // 1.根据关键字搜索全部商品（关键字高亮显示）
        // map.putAll() 代表将 searchList() 返回的 map 中的 k-v 键值对追加到 maps 中
        // k = rows
        maps.putAll(searchList(searchMap));

        // 2.根据关键字查询商品分类（使用 spring data solr 的分组查询 API）
        // k = categoryList
        maps.putAll(searchCategoryList(searchMap));

        // 3.根据商品分类，查询对应的品牌列表和规格列表
        String category = (String) searchMap.get("category");
        if (!"".equals(category)) {
            // 前端传递的搜索条件中包含商品分类，就以前端传递的为准，查询对应的品牌列表和规格列表
            maps.putAll(searchBrandAndSpecList(category));
        } else {
            // 如果前端没有传递商品分类，默认根据商品分类列表中的第一个商品分类名称，查询对应的品牌列表和规格列表
            List<String> categoryList = (List<String>) maps.get("categoryList");
            if (categoryList != null && categoryList.size() > 0) {
                maps.putAll(searchBrandAndSpecList(categoryList.get(0)));
            }
        }

        return maps;
    }


    // 1.根据关键字搜索全部商品（关键字高亮显示）
    private Map<String, Object> searchList(Map searchMap) {

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

        // ****************** 高亮配置
        // 往 query 中添加高亮配置
        HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        // ***************** 构造查询条件
        // 1.1:关键字查询：往 query 中添加查询条件
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        // 1.2:按商品分类进行过滤查询
        if (!"".equals(searchMap.get("category"))) {
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
            filterQuery.addCriteria(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        // 1.3:按品牌进行过滤查询
        if (!"".equals(searchMap.get("brand"))) {
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            filterQuery.addCriteria(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        // 1.4:按规格进行过滤查询
        if (searchMap.get("spec") != null) {
            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
            for (String key : specMap.keySet()) {
                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filterCriteria = new Criteria("item_spec_" + key).is(specMap.get(key));
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        // 1.5:按价格区间进行过滤查询
        if (!"".equals(searchMap.get("price"))) {
            String priceStr = (String) searchMap.get("price");
            if (priceStr != null) {
                String[] price = priceStr.split("-");
                if (!"0".equals(price[0])) { // 左区间不为 0 时，需要设置下界
                    FilterQuery filterQuery = new SimpleFilterQuery();
                    Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(price[0]);
                    filterQuery.addCriteria(filterCriteria);
                    query.addFilterQuery(filterQuery);
                }

                if (!"*".equals(price[1])) { // 右区间不为 * 时，需要设置上界
                    FilterQuery filterQuery = new SimpleFilterQuery();
                    Criteria filterCriteria = new Criteria("item_price").lessThanEqual(price[1]);
                    filterQuery.addCriteria(filterCriteria);
                    query.addFilterQuery(filterQuery);
                }
            }
        }

        // 1.6 设置分页
        Integer pageNo = (Integer)searchMap.get("pageNo");
        if (null == pageNo) {
            pageNo = 1;
        }
        Integer pageSize = (Integer)searchMap.get("pageSize");
        if (null == pageSize) {
            pageSize = 40;
        }

        // 设置分页查询的起始位置
        query.setOffset((pageNo - 1) * pageSize);
        // 设置每页记录数
        query.setRows(pageSize);

        // ***************** 高亮显示处理
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

        map.put("rows", page.getContent()); // 查询结果
        map.put("totalPage", page.getTotalElements());     // 分页查询后的总页数
        map.put("total", page.getTotalElements());      // 分页查询后的总记录数

        return map;
    }

    // 2.根据关键字查询商品分类（使用 spring data solr 的分组查询 API）
    private Map<String, Object> searchCategoryList(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();

        // 构造搜索对象
        Query query = new SimpleQuery("*:*");

        // 添加查询条件
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        // 设置分组选项(item_category 列)
        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(groupOptions);
        // 得到分组页
        GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
        // 根据列得到分组结果(按照某列进行分组查询)
        GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
        // 得到分组结果入口页
        Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
        // 得到分组结果入口页集合
        List<GroupEntry<TbItem>> content = groupEntries.getContent();
        for (GroupEntry<TbItem> entry : content) {
            // 将每一项分组结果的值，封装在集合中，并返回
            list.add(entry.getGroupValue());
        }

        map.put("categoryList", list);

        return map;
    }

    // 3.根据商品分类名称，查询品牌列表和规格列表
    private Map<String, Object> searchBrandAndSpecList(String categoryName) {
        Map map = new HashMap<>();

        // 在缓存中，根据商品分类名称，查询模板 ID
        Long typeId = (Long) redisTemplate.boundHashOps("catNameToTypeID").get(categoryName);
        if (typeId != null) {
            // 在缓存中，根据模板 ID 查询品牌列表和规格列表
            List<Map> brandList = (List<Map>) redisTemplate.boundHashOps("typeIdToBrandList").get(typeId);
            List<Map> specList = (List<Map>) redisTemplate.boundHashOps("typeIdToSpecList").get(typeId);
            map.put("brandList", brandList);
            map.put("specList", specList);
        }

        return map;
    }

}
