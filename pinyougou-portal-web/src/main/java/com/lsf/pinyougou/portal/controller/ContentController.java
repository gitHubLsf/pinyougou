package com.lsf.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbContent;
import com.lsf.pinyougou.service.interfaces.content.ContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 广告控制层
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    /**
     * 查询指定广告分类 ID 下的所有广告
     */
    @RequestMapping("/findByContentCategoryId.do")
    public List<TbContent> findByContentCategoryId(Long categoryId) {
        return contentService.findByContentCategoryId(categoryId);
    }


    @Reference
    private ContentService contentService;
}
