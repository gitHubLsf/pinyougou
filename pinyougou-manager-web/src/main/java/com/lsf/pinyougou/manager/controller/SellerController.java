package com.lsf.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbSeller;
import com.lsf.pinyougou.sellergoods.service.SellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;


/**
 * 商家管理控制层
 *
 * controller
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll.do")
    public List<TbSeller> findAll() {
        return sellerService.findAll();
    }


    /**
     * 无条件分页查询
     *
     * @return
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(int page, int rows) {
        return sellerService.findPage(page, rows);
    }


    /**
     * 多条件分页查询未审核商家列表
     *
     * @param seller
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbSeller seller, int page, int size) {
        return sellerService.findPageLimit(seller, page, size);
    }


    /**
     * 添加，商家入驻申请
     *
     * @param seller
     * @return
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
     * 修改商家的审核状态
     *
     * @param seller
     * @return
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
     * 根据 ID 获取某个未审核商家的全部信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne.do")
    public TbSeller findOne(String id) {
        return sellerService.findOne(id);
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
            sellerService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

}
