package com.lsf.pinyougou.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbSeckillGoods;
import com.lsf.pinyougou.service.interfaces.seckill.SeckillGoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;


/**
 * 秒杀商品控制层
 */
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

    /**
     * 返回正在参与秒杀的上高频
     */
    @RequestMapping("/findList.do")
    public List<TbSeckillGoods> findList() {
        return seckillGoodsService.findList();
    }


    /**
     * 查询某个正在参与秒杀的商品
     *
     * @param id 秒杀商品 ID
     */
    @RequestMapping("/findOneFromCache.do")
    public TbSeckillGoods findOneFromCache(Long id) {
        return seckillGoodsService.findOneFromCache(id);
    }


    /**
     * 返回全部列表
     */
    @RequestMapping("/findAll.do")
    public List<TbSeckillGoods> findAll() {
        return seckillGoodsService.findAll();
    }


    /**
     * 多条件分页查询
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbSeckillGoods seckillGoods, int page, int size) {
        return seckillGoodsService.findPageLimit(seckillGoods, page, size);
    }


    /**
     * 添加
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbSeckillGoods seckillGoods) {
        try {
            seckillGoodsService.add(seckillGoods);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 修改
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbSeckillGoods seckillGoods) {
        try {
            seckillGoodsService.update(seckillGoods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据 ID 获取实体
     */
    @RequestMapping("/findOne.do")
    public TbSeckillGoods findOne(long id) {
        return seckillGoodsService.findOne(id);
    }


    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            seckillGoodsService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    @Reference
    private SeckillGoodsService seckillGoodsService;
}
