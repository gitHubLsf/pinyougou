package com.lsf.pinyougou.manager.controller;

import java.util.List;
import java.util.Map;

import com.lsf.pinyougou.pojogroup.Specification;
import com.lsf.pinyougou.sellergoods.service.SpecificationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbSpecification;

import vo.PageResult;
import vo.Result;


/**
 * 规格列表控制器
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    /**
     * 返回全部规格数据
     */
    @RequestMapping("/findAll.do")
    public List<TbSpecification> findAll() {
        return specificationService.findAll();
    }


    /**
     * 无条件分页查询全部规格
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(int page, int size) {
        return specificationService.findPage(page, size);
    }


    /**
     * 多条件分页查询规格数据
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbSpecification specification, int page, int size) {
        return specificationService.findPageLimit(specification, page, size);
    }


    /**
     * 添加
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody Specification specification) {
        try {
            specificationService.add(specification);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 修改规格
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据 ID 获取某种规格的信息
     */
    @RequestMapping("/findOne.do")
    public Specification findOne(long id) {
        return specificationService.findOne(id);
    }


    /**
     * 批量删除规格
     */
    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            specificationService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    /**
     * 查询所有规格的下拉列表
     */
    @RequestMapping("/selectSpecList.do")
    public List<Map> selectSpecList() {
        try {
            return specificationService.selectSpecList();
        } catch (Exception e) {
            return null;
        }
    }


    @Reference
    private SpecificationService specificationService;
}
