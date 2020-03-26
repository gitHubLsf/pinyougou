package com.lsf.pinyougou.manager.controller;

import java.util.List;
import java.util.Map;

import com.lsf.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbTypeTemplate;

import vo.PageResult;
import vo.Result;

import javax.security.auth.login.CredentialException;


/**
 * controller
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll.do")
    public List<TbTypeTemplate> findAll() {
        return typeTemplateService.findAll();
    }


    /**
     * 多条件分页查询模板数据
     *
     * @param typeTemplate
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbTypeTemplate typeTemplate, int page, int size) {
        return typeTemplateService.findPageLimit(typeTemplate, page, size);
    }


    /**
     * 添加
     *
     * @param typeTemplate
     * @return
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.add(typeTemplate);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    /**
     * 修改模板数据
     *
     * @param typeTemplate
     * @return
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.update(typeTemplate);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne.do")
    public TbTypeTemplate findOne(long id) {
        return typeTemplateService.findOne(id);
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
            typeTemplateService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    /**
     * 添加商品分类时，查询所有类型模板列表
     *
     * @return
     */
    @RequestMapping("/findTypeList.do")
    public List<Map> findTypeList() {
        try {
            return typeTemplateService.findTypeList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 商家添加商品时，需要填写规格列表，此处根据模板 ID 查询模板，再将模板中的规格列表（json 字符串）提取出来，转换成集合
     * 集合里的元素是 Map，用来保存对象
     *
     * @param id    模板 ID
     * @return
     */
    @RequestMapping("/findSpecList.do")
    public List<Map> findSpecList(Long id) {
        return typeTemplateService.findSpecList(id);
    }

}
