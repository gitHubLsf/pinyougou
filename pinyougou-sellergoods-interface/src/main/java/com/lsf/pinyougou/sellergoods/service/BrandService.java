package com.lsf.pinyougou.sellergoods.service;

import com.lsf.pinyougou.pojo.TbBrand;
import vo.PageResult;

import java.util.List;
import java.util.Map;

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


    /**
     * 多条件品牌分页查询
     *
     * @param brand
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult findPageLimit(TbBrand brand, int pageNum, int pageSize);


    /**
     * 添加品牌
     *
     * @param brand
     */
    void add(TbBrand brand);


    /**
     * 根据品牌 ID 查询品牌信息
     *
     * @param id
     * @return
     */
    TbBrand findOne(long id);


    /**
     * 修改品牌信息
     * @param brand
     */
    void update(TbBrand brand);


    /**
     * 批量删除品牌
     *
     * @param ids
     */
    void batchDelete(Long[] ids);


    /**
     * 查询所有品牌下拉列表
     *
     * @return
     */
    List<Map> selectBrandList();
}
