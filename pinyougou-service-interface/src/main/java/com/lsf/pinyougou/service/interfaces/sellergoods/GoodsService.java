package com.lsf.pinyougou.service.interfaces.sellergoods;

import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.pojogroup.Goods;
import vo.PageResult;

import java.util.List;


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
    void updateGoodStatus(Long[] ids, String status) throws Exception;


    /**
     * 修改商品的上下架状态
     *
     * @param ids
     * @param status
     */
    void updateGoodMarketable(Long[] ids, String status);


    /**
     * 批量根据商品 SPU ID 查询商品 SKU，SKU 的状态 status 要等于 1
     */
    List<TbItem> batchSearchItemByGoodId(Long[] ids, String status) throws Exception;
}
