package com.lsf.pinyougou.shop.controller;

import java.util.List;

import com.lsf.pinyougou.pojogroup.Goods;
import com.lsf.pinyougou.sellergoods.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbGoods;

import vo.PageResult;
import vo.Result;


/**
 * controller
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll.do")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 无条件分页查询
     *
     * @return
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }


    /**
     * 商家查询自己的全部列表
     * 多条件分页查询
     *
     * @param goods
     * @param page
     * @param size
     * @return
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
     *
     * @param goods
     * @return
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
     *
     * @param goods
     * @return
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
            goodsService.update(goods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据商品 ID 查询商品信息，返回商品组合实体类对象
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne.do")
    public Goods findOne(long id) {
        return goodsService.findOne(id);
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
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


    /**
     * 商家批量修改商品的上下架状态
     *
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping("/updateGoodMarketable.do")
    public Result updateGoodMarketable(Long[] ids, String status) {
        try {
            goodsService.updateGoodMarketable(ids, status);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }

}
