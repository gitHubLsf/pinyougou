package com.lsf.pinyougou.sellergoods.service;

import java.util.List;

import com.lsf.pinyougou.pojo.TbItemCat;
import com.lsf.pinyougou.pojogroup.TbItemCats;
import vo.PageResult;


/**
 * 商品分类服务层接口
 */
public interface ItemCatService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbItemCat> findAll();


    /**
     * 无条件分查询
     *
     * @return
     */
    PageResult findPage(int pageNum, int pageSize);


    /**
     * 多条件分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页记录数
     * @return
     */
    PageResult findPageLimit(TbItemCat itemCat, int pageNum, int pageSize);


    /**
     * 添加商品分类
     */
    void add(TbItemCat itemCat);


    /**
     * 修改
     */
    void update(TbItemCat itemCat);


    /**
     * 根据 ID 查询某种商品分类
     *
     * @param id
     * @return
     */
    TbItemCats findOne(long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids) throws Exception;


    /**
     * 根据上级 ID 查询商品分类
     *
     * @param parentId
     * @return
     */
    List<TbItemCat> findByParentId(Long parentId);

}
