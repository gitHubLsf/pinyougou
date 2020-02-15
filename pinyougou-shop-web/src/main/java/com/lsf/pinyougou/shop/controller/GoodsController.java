package com.lsf.pinyougou.shop.controller;

import java.util.List;

import com.lsf.pinyougou.sellergoods.service.GoodsService;
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
     * 多条件分页查询
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbGoods goods, int page, int rows) {
        return goodsService.findPageLimit(goods, page, rows);
    }


    /**
     * 添加
     *
     * @param goods
     * @return
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbGoods goods) {
        try {
            goodsService.add(goods);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 修改
     *
     * @param goods
     * @return
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbGoods goods) {
        try {
            goodsService.update(goods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne.do")
    public TbGoods findOne(long id) {
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

}
