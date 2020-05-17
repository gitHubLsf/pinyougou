package com.lsf.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbBrand;
import com.lsf.pinyougou.sellergoods.service.BrandService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;
import java.util.Map;


/**
 * 运营商管理后台-品牌列表控制器
 *
 * @RestController 是 Responsebody + Controller 的组合
 */
@RestController
@RequestMapping("/brand")
public class BrandController {


    /**
     * 查询所有品牌
     *
     * 此处返回的集合会被 springMVC 管理的 fastJSON 转换器转换为 json 数据传给 Web 消费方
     */
    @RequestMapping("/findAll.do")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }


    /**
     * 品牌分页
     *
     * @param page  当前页码
     * @param size  每页记录数
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(int page, int size) {
        return brandService.findPage(page, size);
    }


    /**
     * 多条件品牌分页查询
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbBrand brand, int page, int size) {
        return brandService.findPageLimit(brand, page, size);
    }


    /**
     * 添加品牌
     *
     * @param brand 前端传递的封装对象,需要使用 @RequestBody 注解标记
     * @return  Result 是给前端的响应体
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbBrand brand) {
        try {
            brandService.add(brand);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 根据品牌 ID 查询品牌信息
     */
    @RequestMapping("/findOne.do")
    public TbBrand findOne(long id) {
        return brandService.findOne(id);
    }


    /**
     * 修改品牌信息
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbBrand brand) {
        try {
            brandService.update(brand);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 批量删除品牌
     */
    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            brandService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    /**
     * 查询所有品牌的列表
     */
    @RequestMapping("/selectBrandList.do")
    public List<Map> selectBrandList() {
        try {
            return brandService.selectBrandList();
        } catch (Exception e) {
            return null;
        }
    }


    @Reference
    private BrandService brandService;
}
