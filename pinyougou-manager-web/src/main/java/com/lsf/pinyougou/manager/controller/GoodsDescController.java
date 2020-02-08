package com.lsf.pinyougou.manager.controller;

import java.util.List;

import com.lsf.pinyougou.sellergoods.service.GoodsDescService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbGoodsDesc;

import vo.PageResult;
import vo.Result;


/**
 * controller
 */
@RestController
@RequestMapping("/goodsDesc")
public class GoodsDescController {

    @Reference
    private GoodsDescService goodsDescService;


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll.do")
    public List<TbGoodsDesc> findAll() {
        return goodsDescService.findAll();
    }


    /**
     * 无条件分页查询
     *
     * @return
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(int page, int rows) {
        return goodsDescService.findPage(page, rows);
    }


    /**
     * 多条件分页查询
     *
     * @param goodsDesc
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbGoodsDesc goodsDesc, int page, int rows) {
        return goodsDescService.findPageLimit(goodsDesc, page, rows);
    }


    /**
     * 添加
     *
     * @param goodsDesc
     * @return
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbGoodsDesc goodsDesc) {
        try {
            goodsDescService.add(goodsDesc);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 修改
     *
     * @param goodsDesc
     * @return
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbGoodsDesc goodsDesc) {
        try {
            goodsDescService.update(goodsDesc);
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
    public TbGoodsDesc findOne(long id) {
        return goodsDescService.findOne(id);
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
            goodsDescService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

}
