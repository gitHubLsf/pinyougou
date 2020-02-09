package com.lsf.pinyougou.manager.controller;

import java.util.List;

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

    @Reference
    private SpecificationService specificationService;


    /**
     * 返回全部规格数据
     *
     * @return
     */
    @RequestMapping("/findAll.do")
    public List<TbSpecification> findAll() {
        return specificationService.findAll();
    }


    /**
     * 无条件分页查询全部规格
     *
     * @return
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(int page, int size) {
        return specificationService.findPage(page, size);
    }


    /**
     * 多条件分页查询规格数据
     *
     * @param specification
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbSpecification specification, int page, int size) {
        return specificationService.findPageLimit(specification, page, size);
    }


    /**
     * 添加
     *
     * @param specification
     * @return
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
     * 修改
     *
     * @param specification
     * @return
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbSpecification specification) {
        try {
            specificationService.update(specification);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据 ID 获取某种规格的信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne.do")
    public Specification findOne(long id) {
        return specificationService.findOne(id);
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
            specificationService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

}