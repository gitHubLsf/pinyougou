package com.lsf.pinyougou.service.interfaces.sellergoods;

import com.lsf.pinyougou.pojo.TbItemCat;
import com.lsf.pinyougou.pojogroup.TbItemCats;
import vo.PageResult;

import java.util.List;


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
