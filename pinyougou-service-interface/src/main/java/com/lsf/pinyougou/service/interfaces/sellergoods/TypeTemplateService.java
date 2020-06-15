package com.lsf.pinyougou.service.interfaces.sellergoods;

import com.lsf.pinyougou.pojo.TbTypeTemplate;
import vo.PageResult;

import java.util.List;
import java.util.Map;


/**
 * 服务层接口
 */
public interface TypeTemplateService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbTypeTemplate> findAll();


    /**
     * 多条件分页查询模板数据
     *
     * @param pageNum  当前页
     * @param pageSize 每页记录数
     * @return
     */
    PageResult findPageLimit(TbTypeTemplate typeTemplate, int pageNum, int pageSize);


    /**
     * 添加
     */
    void add(TbTypeTemplate typeTemplate);


    /**
     * 修改
     */
    void update(TbTypeTemplate typeTemplate);


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    TbTypeTemplate findOne(long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(Long[] ids);


    /**
     * 添加商品分类时，查询所有类型模板列表
     *
     * @return
     */
    List<Map> findTypeList();


    /**
     * 商家添加商品时，需要填写规格列表，此处根据模板 ID 查询模板，再将模板中的规格列表（json 字符串）提取出来，转换成集合
     * 集合里的元素是 Map，用来保存对象
     *
     * @param id
     * @return
     */
    List<Map> findSpecList(Long id);
}
