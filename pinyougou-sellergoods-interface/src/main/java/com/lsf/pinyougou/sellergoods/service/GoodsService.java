package com.lsf.pinyougou.sellergoods.service;

import java.util.List;

import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojogroup.Goods;
import vo.PageResult;


/**
 * 商品服务层接口
 */
public interface GoodsService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbGoods> findAll();


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
    PageResult findPageLimit(TbGoods goods, int pageNum, int pageSize);


    /**
     * 添加商品
     */
    void add(Goods goods);


    /**
     * 修改商品信息
     */
    void update(Goods goods);


    /**
     * 根据商品 ID 查询商品信息，返回商品组合实体类对象 Goods
     *
     * @param id
     * @return
     */
    Goods findOne(long id);


    /**
     * 批量删除商品，只进行逻辑删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids);


    /**
     * 修改商品的审核状态
     */
    void updateGoodStatus(Long[] ids, String status);


    /**
     * 修改商品的上下架状态
     *
     * @param ids
     * @param status
     */
    void updateGoodMarketable(Long[] ids, String status);
}
