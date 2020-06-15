package com.lsf.pinyougou.service.interfaces.sellergoods;

import com.lsf.pinyougou.pojo.TbSpecification;
import com.lsf.pinyougou.pojogroup.Specification;
import vo.PageResult;

import java.util.List;
import java.util.Map;


/**
 * 服务层接口
 */
public interface SpecificationService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbSpecification> findAll();


    /**
     * 多条件分页查询规格数据
     *
     * @param pageNum  当前页
     * @param pageSize 每页记录数
     * @return
     */
    PageResult findPageLimit(TbSpecification specification, int pageNum, int pageSize);


    /**
     * 添加规格
     */
    void add(Specification specification);


    /**
     * 修改规格
     */
    void update(Specification specification);


    /**
     * 根据 ID 获取某种规格的信息
     *
     * @param id
     * @return
     */
    Specification findOne(long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids);


    /**
     * 查询所有规格下拉列表
     *
     * @return
     */
    List<Map> selectSpecList();

}
