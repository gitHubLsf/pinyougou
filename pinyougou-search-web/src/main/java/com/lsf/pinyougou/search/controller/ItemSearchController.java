package com.lsf.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.search.service.ItemSearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 商品搜索控制层
 */
@RestController
@RequestMapping("/itemSearch")
public class ItemSearchController {

    @RequestMapping("/search.do")
    public Map<String, Object> search(@RequestBody Map searchMap) {
        return itemSearchService.search(searchMap);
    }


    @Reference
    //@Reference(timeout = 5000)
    private ItemSearchService itemSearchService;
}
