package com.lsf.pinyougou.shop.controller;

import java.util.Arrays;
import java.util.List;

import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.pojogroup.Goods;
import com.lsf.pinyougou.search.service.ItemSearchService;
import com.lsf.pinyougou.sellergoods.service.GoodsService;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbGoods;

import vo.PageResult;
import vo.Result;


@RestController
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 返回全部列表
     */
    @RequestMapping("/findAll.do")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 无条件分页查询
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }


    /**
     * 商家查询自己的全部列表
     * 多条件分页查询
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbGoods goods, int page, int size) {
        // 获取商家 ID
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(sellerId);
        return goodsService.findPageLimit(goods, page, size);
    }


    /**
     * 添加商品
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody Goods goods) {
        try {
            // 设置商品所属的商家 ID 为当前在线商家
            // 获取在线商家 ID
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goods.getTbGoods().setSellerId(sellerId);

            goodsService.add(goods);

            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 商家修改自己的商品信息
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody Goods goods) {
        // 出于安全考虑，在商户后台执行的商品修改，必须要校验提交的商品属于该商户

        // 判断商品的商家 ID 是否和当前在线商家一致
        String selledId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 判断商品的商家 ID 是否和数据库中的商家 ID 一致
        Goods dbGoods = goodsService.findOne(goods.getTbGoods().getId());

        if (!selledId.equals(goods.getTbGoods().getSellerId())
            || !dbGoods.getTbGoods().getSellerId().equals(selledId)) {
            return new Result(false, "非法操作");
        }

        // 后续还要检验商品详情和商品 SKU 中的商品 ID 是否和商品的 ID 一致
        // 因为可能商品的 ID 正确，但是商品详情和商品 SKU 中的商品 ID 被修改了
        // 会导致修改了别的商品详情或者商品 SKU

        try {
            // 修改数据库中的商品信息
            goodsService.update(goods);

            // 删除 solr 中，商品对应的 SKU
            Long[] ids = new Long[1];
            ids[0] = goods.getTbGoods().getId();
            itemSearchService.batchDeleteItem(Arrays.asList(ids));
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据商品 ID 查询商品信息，返回商品组合实体类对象
     */
    @RequestMapping("/findOne.do")
    public Goods findOne(long id) {
        return goodsService.findOne(id);
    }


    /**
     * 商家批量删除商品
     */
    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            // 删除数据库中的商品
            goodsService.batchDelete(ids);
            // 删除 solr 中的商品
            itemSearchService.batchDeleteItem(Arrays.asList(ids));
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    /**
     * 商家批量修改商品的上下架状态
     */
    @RequestMapping("/updateGoodMarketable.do")
    public Result updateGoodMarketable(Long[] ids, String status) {
        if (ids == null || ids.length == 0)
            return new Result(false, "修改失败");
        try {
            // 修改数据库中商品的上下架状态
            goodsService.updateGoodMarketable(ids, status);
            // 同步数据到 solr 中
            // 如果是上架商品
            if ("1".equals(status)) {
                // 往 solr 中添加 SKU
                // 查询对应的 SKU
                List<TbItem> itemList = goodsService.batchSearchItemByGoodId(ids, "1");
                // 保存到 solr 中
                itemSearchService.batchImportItem(itemList);
            } else if ("0".equals(status)) {
                // 下架商品
                // 删除 solr 中相关的 SKU
                itemSearchService.batchDeleteItem(Arrays.asList(ids));
            }
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    @Reference
    private GoodsService goodsService;


    @Reference(timeout = 100000)
    private ItemSearchService itemSearchService;
}
