package com.lsf.pinyougou.sellergoods.service;

import com.lsf.pinyougou.pojo.TbBrand;

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
}
