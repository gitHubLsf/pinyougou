//package com.lsf.pinyougou.manager.controller;
//
//import java.util.List;
//
//import com.lsf.pinyougou.pojogroup.TbItemCats;
//import com.lsf.pinyougou.sellergoods.service.ItemCatService;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.lsf.pinyougou.pojo.TbItemCat;
//
//import vo.PageResult;
//import vo.Result;
//
//
///**
// * 商品分类控制层
// */
//@RestController
//@RequestMapping("/itemCat")
//public class ItemCatController {
//
//    @Reference
//    private ItemCatService itemCatService;
//
//
//    /**
//     * 返回全部列表
//     *
//     * @return
//     */
//    @RequestMapping("/findAll.do")
//    public List<TbItemCat> findAll() {
//        return itemCatService.findAll();
//    }
//
//
//    /**
//     * 无条件分页查询
//     *
//     * @return
//     */
//    @RequestMapping("/findPage.do")
//    public PageResult findPage(int page, int rows) {
//        return itemCatService.findPage(page, rows);
//    }
//
//
//    /**
//     * 多条件分页查询
//     *
//     * @param itemCat
//     * @param page
//     * @param rows
//     * @return
//     */
//    @RequestMapping("/search.do")
//    public PageResult findPageLimit(@RequestBody TbItemCat itemCat, int page, int rows) {
//        return itemCatService.findPageLimit(itemCat, page, rows);
//    }
//
//
//    /**
//     * 添加商品分类
//     *
//     * @param itemCat
//     * @return
//     */
//    @RequestMapping("/add.do")
//    public Result add(@RequestBody TbItemCat itemCat) {
//        try {
//            itemCatService.add(itemCat);
//            return new Result(true, "添加成功");
//        } catch (Exception e) {
//            return new Result(false, "添加失败");
//        }
//    }
//
//
//    /**
//     * 修改
//     *
//     * @param itemCat
//     * @return
//     */
//    @RequestMapping("/update.do")
//    public Result update(@RequestBody TbItemCat itemCat) {
//        try {
//            itemCatService.update(itemCat);
//            return new Result(true, "修改成功");
//        } catch (Exception e) {
//            return new Result(false, "修改失败");
//        }
//    }
//
//
//    /**
//     * 根据 ID 查询某种商品分类
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("/findOne.do")
//    public TbItemCats findOne(long id) {
//        return itemCatService.findOne(id);
//    }
//
//
//    /**
//     * 批量删除商品分类
//     *
//     * @param ids
//     * @return
//     */
//    @RequestMapping("/batchDelete.do")
//    public Result batchDelete(Long[] ids) {
//        try {
//            itemCatService.batchDelete(ids);
//            return new Result(true, "删除成功");
//        } catch (Exception e) {
//            return new Result(false, "删除失败");
//        }
//    }
//
//
//    /**
//     * 根据上级 ID 查询商品分类
//     *
//     * @param parentId
//     * @return
//     */
//    @RequestMapping("/findByParentId.do")
//    public List<TbItemCat> findByParentId(Long parentId) {
//        return itemCatService.findByParentId(parentId);
//    }
//
//}
