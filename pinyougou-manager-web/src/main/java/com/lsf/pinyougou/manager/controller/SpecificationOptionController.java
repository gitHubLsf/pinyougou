package com.lsf.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbSpecificationOption;
import com.lsf.pinyougou.service.interfaces.sellergoods.SpecificationOptionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;


@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {

    /**
     * 返回全部列表
     */
    @RequestMapping("/findAll.do")
    public List<TbSpecificationOption> findAll() {
        return specificationOptionService.findAll();
    }


    /**
     * 多条件分页查询
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbSpecificationOption specificationOption, int page, int rows) {
        return specificationOptionService.findPageLimit(specificationOption, page, rows);
    }


    /**
     * 添加
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbSpecificationOption specificationOption) {
        try {
            specificationOptionService.add(specificationOption);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 修改
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbSpecificationOption specificationOption) {
        try {
            specificationOptionService.update(specificationOption);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据 ID 获取实体
     */
    @RequestMapping("/findOne.do")
    public TbSpecificationOption findOne(long id) {
        return specificationOptionService.findOne(id);
    }


    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            specificationOptionService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    @Reference
    private SpecificationOptionService specificationOptionService;
}
