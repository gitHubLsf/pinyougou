package com.lsf.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbSeller;
import com.lsf.pinyougou.service.interfaces.sellergoods.SellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;


/**
 * 商家管理控制层
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    /**
     * 返回全部列表
     */
    @RequestMapping("/findAll.do")
    public List<TbSeller> findAll() {
        return sellerService.findAll();
    }


    /**
     * 多条件分页查询
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbSeller seller, int page, int rows) {
        return sellerService.findPageLimit(seller, page, rows);
    }


    /**
     * 添加，商家入驻申请
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbSeller seller) {
        try {
            sellerService.add(seller);
            return new Result(true, "申请成功");
        } catch (Exception e) {
            return new Result(false, "申请失败");
        }
    }


    /**
     * 修改
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbSeller seller) {
        try {
            sellerService.update(seller);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据 ID 获取实体
     */
    @RequestMapping("/findOne.do")
    public TbSeller findOne(String id) {
        return sellerService.findOne(id);
    }


    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            sellerService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    @Reference
    private SellerService sellerService;
}
