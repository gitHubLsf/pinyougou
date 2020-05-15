package com.lsf.pinyougou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;


/**
 * 商品搜索服务的 activeMQ 监听器
 */
@Component
public class ItemSearchListener implements MessageListener {

    /**
     * 此处为本地依赖
     */
    @Autowired
    private ItemSearchService itemSearchService;


    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();

            // 将消息内容（json 字符串）转成集合
            List<TbItem> itemList = JSON.parseArray(text, TbItem.class);

            // 调用商品搜索服务将集合中的商品数据导入到 solr
            itemSearchService.batchImportItem(itemList);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
