package com.lsf.pinyougou.manager.controller;

import java.util.List;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.content.service.ContentService;
import com.lsf.pinyougou.pojo.TbContent;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;


/**
 * 广告控制
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;


    @RequestMapping("/findAll.do")
    public List<TbContent> findAll() {
        return contentService.findAll();
    }


    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbContent content, int page, int size) {
        return contentService.findPageLimit(content, page, size);
    }


    @RequestMapping("/add.do")
    public Result add(@RequestBody TbContent content) {
        try {
            contentService.add(content);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    @RequestMapping("/update.do")
    public Result update(@RequestBody TbContent content) {
        try {
            contentService.update(content);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    @RequestMapping("/findOne.do")
    public TbContent findOne(long id) {
        return contentService.findOne(id);
    }


    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            contentService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

}
