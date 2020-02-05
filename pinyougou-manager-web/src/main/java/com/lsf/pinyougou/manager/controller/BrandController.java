package com.lsf.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.container.page.Page;
import com.lsf.pinyougou.pojo.TbBrand;
import com.lsf.pinyougou.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;

import java.util.List;

/**
 * 运营商管理后台-品牌列表控制器
 *
 * @author linshaofeng
 * @date 2020/2/4 13:06
 */

/**
 * @RestController 是 Responsebody + Controller 的组合
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;


    /**
     * 查询所有品牌
     *
     * 此处返回的集合会被 springMVC 管理的 fastJSON 转换器转换为 json 数据传给 Web 消费方
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }


    /**
     * 品牌分页
     *
     * @param page  当前页码
     * @param size  每页记录数
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int size) {
        return brandService.findPage(page, size);
    }

}
