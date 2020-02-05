package com.lsf.pinyougou.sellergoods.service;

import com.lsf.pinyougou.pojo.TbBrand;
import vo.PageResult;

import java.util.List;

/**
 * 品牌列表服务接口
 *
 * @author linshaofeng
 * @date 2020/2/4 13:00
 */
public interface BrandService {

    /**
     * 查询所有品牌
     * @return
     */
    List<TbBrand> findAll();


    /**
     * 品牌分页
     *
     * @param pageNum   当前页码
     * @param pageSize  每页显示的记录数
     * @return
     */
    PageResult findPage(int pageNum, int pageSize);
}
