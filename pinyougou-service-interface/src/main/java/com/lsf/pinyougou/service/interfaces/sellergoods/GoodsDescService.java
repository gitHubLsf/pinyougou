package com.lsf.pinyougou.service.interfaces.sellergoods;

import com.lsf.pinyougou.pojo.TbGoodsDesc;
import vo.PageResult;

import java.util.List;


/**
 * 服务层接口
 */
public interface GoodsDescService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbGoodsDesc> findAll();



    /**
     * 多条件分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页记录数
     * @return
     */
    PageResult findPageLimit(TbGoodsDesc goodsDesc, int pageNum, int pageSize);


    /**
     * 添加
     */
    void add(TbGoodsDesc goodsDesc);


    /**
     * 修改
     */
    void update(TbGoodsDesc goodsDesc);


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    TbGoodsDesc findOne(long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids);

}
