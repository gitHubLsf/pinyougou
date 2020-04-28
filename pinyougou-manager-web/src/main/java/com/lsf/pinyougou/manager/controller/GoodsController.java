package com.lsf.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.pojogroup.Goods;
import com.lsf.pinyougou.search.service.ItemSearchService;
import com.lsf.pinyougou.sellergoods.service.GoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

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
    public Result updateGoodStatus(Long[] ids, String status) {
        if (ids == null || ids.length == 0)
            return new Result(false, "修改失败");
        try {
            goodsService.updateGoodStatus(ids, status);
            // 如果是修改商品的审核状态为已审核 1，需要将审核通过的商品的 SKU 导入到 solr 中
            if ("1".equals(status)) {
                // 批量根据商品 SPU ID 查询商品 SKU，SKU 的 status 必须为 "1"，代表 SKU 状态正常
                List<TbItem> itemList = goodsService.batchSearchItemByGoodId(ids, "1");
                // 将查询到的 SKU 数据导入到 solr 中
                itemSearchService.batchImportItem(itemList);
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


    @Reference(timeout = 100000)
    private ItemSearchService itemSearchService;

}
