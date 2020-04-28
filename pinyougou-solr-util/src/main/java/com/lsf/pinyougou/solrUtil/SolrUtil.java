package com.lsf.pinyougou.solrUtil;

import com.alibaba.fastjson.JSON;
import com.lsf.pinyougou.dao.TbItemDao;
import com.lsf.pinyougou.pojo.TbItem;
import org.opensaml.ws.wssecurity.impl.SaltImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {

    @Autowired
    private TbItemDao tbItemDao;

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 查询全部 SKU，状态 status 字段为 1
     */
    public void queryAllItem() {
        TbItem tbItem = new TbItem();
        tbItem.setStatus("1");
        List<TbItem> items = tbItemDao.queryAll(tbItem);
        int i = 0;
        for (TbItem item : items) {
            System.out.println(++i + "+" + item);
        }
    }


    /**
     * 批量添加 SKU 到 solr 中
     */
    public void batchImportItemToSolr() {
        TbItem tbItem = new TbItem();
        tbItem.setStatus("1");
        List<TbItem> items = tbItemDao.queryAll(tbItem);

        for (TbItem item : items) {
            // 查询 spec 字段，是 json 字符串
            String spec = item.getSpec();
            // 将其转换成对象形式
            Map specMap
                    = JSON.parseObject(spec, Map.class);
            // 填充到 TbItem 中的 specMap 字段，它映射 solr 中的 item_spec_* 动态域
            item.setSpecMap(specMap);
        }

        solrTemplate.saveBeans(items);
        solrTemplate.commit();
    }

    public void delete() {
        Query query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    public static void main(String[] args) {
        // 加载 spring 配置文件
        ApplicationContext context
                = new ClassPathXmlApplicationContext("classpath*:applicationContext*.xml");

        SolrUtil solrUtil = (SolrUtil)context.getBean("solrUtil");

        // 查询所有 SKU
        //solrUtil.queryAllItem();

        //solrUtil.delete();

        // 批量导入 SKU 到 solr 中
        solrUtil.batchImportItemToSolr();
    }
}
