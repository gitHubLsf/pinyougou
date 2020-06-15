package com.lsf.pinyougou.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbSeckillOrder;
import com.lsf.pinyougou.service.interfaces.seckill.SeckillOrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;


/**
 * 秒杀订单控制层
 */
@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll.do")
    public List<TbSeckillOrder> findAll() {
        return seckillOrderService.findAll();
    }


    /**
     * 多条件分页查询
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbSeckillOrder seckillOrder, int page, int size) {
        return seckillOrderService.findPageLimit(seckillOrder, page, size);
    }


    /**
     * 添加
     *
     * @param seckillOrder
     * @return
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbSeckillOrder seckillOrder) {
        try {
            seckillOrderService.add(seckillOrder);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 修改
     *
     * @param seckillOrder
     * @return
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbSeckillOrder seckillOrder) {
        try {
            seckillOrderService.update(seckillOrder);
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
    public TbSeckillOrder findOne(long id) {
        return seckillOrderService.findOne(id);
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
            seckillOrderService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    @Reference
    private SeckillOrderService seckillOrderService;

}
