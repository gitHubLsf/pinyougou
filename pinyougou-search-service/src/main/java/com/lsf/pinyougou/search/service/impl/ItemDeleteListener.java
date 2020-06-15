package com.lsf.pinyougou.search.service.impl;

import com.lsf.pinyougou.service.interfaces.search.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Arrays;


/**
 * 从 solr 中删除数据的 activeMQ 监听器
 */
@Component
public class ItemDeleteListener implements MessageListener {

    /**
     * 此处为本地依赖
     */
    @Autowired
    private ItemSearchService itemSearchService;


    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Long[] goodIds = (Long[]) objectMessage.getObject();

            // 调用商品搜索服务从 solr 中删除 SKU
            itemSearchService.batchDeleteItem(Arrays.asList(goodIds));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
