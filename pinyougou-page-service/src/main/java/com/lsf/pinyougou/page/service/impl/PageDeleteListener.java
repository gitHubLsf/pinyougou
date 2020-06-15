package com.lsf.pinyougou.page.service.impl;

import com.lsf.pinyougou.service.interfaces.page.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


/**
 * 用于删除生成的商品详情静态页的 activeMQ 监听器
 */
@Component
public class PageDeleteListener implements MessageListener {

    /**
     * 本地注入
     */
    @Autowired
    private ItemPageService itemPageService;


    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Long[] goodsId = (Long[])objectMessage.getObject();

            itemPageService.deleteItemHtml(goodsId);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
