package com.lsf.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.pojogroup.Goods;
import com.lsf.pinyougou.service.interfaces.sellergoods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;


@RestController
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 运行商查询所有未审核商品列表
     * 多条件分页查询
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbGoods goods, int page, int size) {
        return goodsService.findPageLimit(goods, page, size);
    }


    /**
     * 根据商品 ID 查询商品信息，返回商品组合实体类对象
     */
    @RequestMapping("/findOne.do")
    public Goods findOne(long id) {
        return goodsService.findOne(id);
    }


    /**
     * 运营商批量修改商品的审核状态
     */
    @RequestMapping("/updateGoodStatus.do")
    public Result updateGoodStatus(final Long[] ids, String status) {
        if (ids == null || ids.length == 0)
            return new Result(false, "修改失败");
        try {
            goodsService.updateGoodStatus(ids, status);
            // 如果是修改商品的审核状态为已审核 1，需要将审核通过的商品的 SKU 导入到 solr 中
            // 同时生成商品详情页静态页面
            if ("1".equals(status)) {
                // 生成商品详情页静态页面
                // 此处改为基于消息队列的静态调用
                //itemPageService.getItemHtml(ids);

                jmsTemplate.send(topicPageCreateDestination, new MessageCreator() {

                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createObjectMessage(ids);
                    }
                });

                // 批量根据商品 SPU ID 查询商品 SKU，SKU 的 status 必须为 "1"，代表 SKU 状态正常
                List<TbItem> itemList = goodsService.batchSearchItemByGoodId(ids, "1");

                // 将查询到的 SKU 数据导入到 solr 中
                // 此处是同步调用，修改成消息队列的异步调用
                //itemSearchService.batchImportItem(itemList);

                // 将查询到的 SKU 数据导入到 solr 中
                // 此处采用消息传递机制进行异步调用即可
                // 先将要导入的数据 itemList 集合转换成 json 字符串，然后将其作为字符串类型的消息正文格式进行消息发送
                final String itemListString = JSON.toJSONString(itemList);
                jmsTemplate.send(queueSolrImportDestination, new MessageCreator() {

                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(itemListString);
                    }
                });
            }

            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 批量删除商品，只进行逻辑删除
     */
    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            goodsService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    @Reference
    private GoodsService goodsService;


    // 此处解耦搜索服务模块，改成消息队列的异步调用
    //@Reference(timeout = 40000)
    //private ItemSearchService itemSearchService;


    @Autowired
    private JmsTemplate jmsTemplate;


    /**
     * activeMQ 中运营商后台导入数据到solr的队列
     */
    @Autowired
    private Destination queueSolrImportDestination;


    /**
     * 此处改为基于消息队列的异步调用
     */
    //@Reference(timeout = 40000)
    //private ItemPageService itemPageService;


    /**
     * activeMQ 中运营商后台生成商品详情静态页的队列
     */
    @Autowired
    private Destination topicPageCreateDestination;
}
