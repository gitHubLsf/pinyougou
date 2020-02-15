package com.lsf.pinyougou.sellergoods.service;

import java.util.List;

import com.lsf.pinyougou.pojo.TbSeller;
import vo.PageResult;


/**
 * 服务层接口
 */
public interface SellerService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbSeller> findAll();


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
    PageResult findPageLimit(TbSeller seller, int pageNum, int pageSize);


    /**
     * 添加
     */
    void add(TbSeller seller);


    /**
     * 修改商家的审核状态
     */
    void update(TbSeller seller);


    /**
     * 根据 ID 查询某个未审核商家的全部信息
     *
     * @param id
     * @return
     */
    TbSeller findOne(String id);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids);

}
