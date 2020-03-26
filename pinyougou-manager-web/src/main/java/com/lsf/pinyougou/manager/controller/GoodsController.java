package com.lsf.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojogroup.Goods;
import com.lsf.pinyougou.sellergoods.service.GoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
     *
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping("/updateGoodStatus.do")
    public Result updateGoodStatus(Long[] ids, String status) {
        try {
            goodsService.updateGoodStatus(ids, status);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 批量删除商品，只进行逻辑删除
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
}
