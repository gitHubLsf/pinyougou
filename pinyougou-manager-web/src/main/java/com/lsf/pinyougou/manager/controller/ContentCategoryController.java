package com.lsf.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.content.service.ContentCategoryService;
import com.lsf.pinyougou.pojo.TbContentCategory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;


/**
 * 广告分类控制
 */
@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {

    @RequestMapping("/findAll.do")
    public List<TbContentCategory> findAll() {
        return contentCategoryService.findAll();
    }


    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbContentCategory contentCategory, int page, int size) {
        return contentCategoryService.findPageLimit(contentCategory, page, size);
    }


    @RequestMapping("/add.do")
    public Result add(@RequestBody TbContentCategory contentCategory) {
        try {
            contentCategoryService.add(contentCategory);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    @RequestMapping("/update.do")
    public Result update(@RequestBody TbContentCategory contentCategory) {
        try {
            contentCategoryService.update(contentCategory);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    @RequestMapping("/findOne.do")
    public TbContentCategory findOne(long id) {
        return contentCategoryService.findOne(id);
    }


    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            contentCategoryService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    @Reference
    private ContentCategoryService contentCategoryService;
}
