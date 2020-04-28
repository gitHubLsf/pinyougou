package com.lsf.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbSpecificationOptionDao;
import com.lsf.pinyougou.pojo.TbSpecificationOption;
import com.lsf.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lsf.pinyougou.dao.TbTypeTemplateDao;
import com.lsf.pinyougou.pojo.TbTypeTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import vo.PageResult;

import javax.annotation.PostConstruct;


/**
 * 模板服务实现层
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @PostConstruct
    private void init() {
        // 当对象创建时，提前往 redis 缓存中添加品牌缓存和规格缓存
        if (tbTypeTemplateDao != null && redisTemplate != null) {
            List<TbTypeTemplate> list = tbTypeTemplateDao.queryAll(new TbTypeTemplate());
            for (TbTypeTemplate t : list) {
                // 构造品牌列表集合
                List<Map> brandList = JSON.parseArray(t.getBrandIds(), Map.class);
                redisTemplate.boundHashOps("typeIdToBrandList").put(t.getId(), brandList);
                // 构造规格列表集合
                List<Map> specList = findSpecList(t.getId());
                redisTemplate.boundHashOps("typeIdToSpecList").put(t.getId(), specList);
            }
        }
    }


    /**
     * 查询全部
     */
    @Override
    public List<TbTypeTemplate> findAll() {
        return tbTypeTemplateDao.queryAll(null);
    }


    /**
     * 无条件分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbTypeTemplate> list = tbTypeTemplateDao.queryAll(null);
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbTypeTemplate> list = tbTypeTemplateDao.queryAllLimit(typeTemplate);
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加（往数据库中添加，往缓存中添加）
     */
    @Override
    public void add(TbTypeTemplate typeTemplate) {
        // 往数据库中添加模板
        tbTypeTemplateDao.insert(typeTemplate);

        // 往 typeIdToBrandList 缓存中添加品牌缓存
        List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
        redisTemplate.boundHashOps("typeIdToBrandList").put(typeTemplate.getId(), brandList);

        // 往 缓存中添加规格缓存
        List<Map> specList = findSpecList(typeTemplate.getId());
        redisTemplate.boundHashOps("typeIdToSpecList").put(typeTemplate.getId(), specList);
    }


    /**
     * 修改（修改数据库中的内容，更新缓存中的内容）
     */
    @Override
    public void update(TbTypeTemplate typeTemplate) {
        // 修改数据库中的内容
        tbTypeTemplateDao.update(typeTemplate);

        // 更新缓存中的内容
        List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
        redisTemplate.boundHashOps("typeIdToBrandList").put(typeTemplate.getId(), brandList);
        List<Map> specList = findSpecList(typeTemplate.getId());
        redisTemplate.boundHashOps("typeIdToSpecList").put(typeTemplate.getId(), specList);
    }


    /**
     * 根据 ID 获取实体
     */
    @Override
    public TbTypeTemplate findOne(long id) {
        return tbTypeTemplateDao.queryById(id);
    }


    /**
     * 批量删除（删除数据库，删除缓存中的旧内容）
     */
    @Override
    public void batchDelete(Long[] ids) {
        if (ids != null && ids.length > 0) {
            // 根据模板 ID 批量删除数据库中的模板
            tbTypeTemplateDao.batchDeleteById(ids);

            for (Long id : ids) {
                // 删除缓存中的旧内容
                redisTemplate.boundHashOps("typeIdToBrandList").delete(id);
                redisTemplate.boundHashOps("typeIdToSpecList").delete(id);
            }
        }
    }


    /**
     * 添加商品分类时，查询所有类型模板列表
     */
    @Override
    public List<Map> findTypeList() {
        return tbTypeTemplateDao.findTypeList();
    }


    /**
     * 商家添加商品时，需要填写规格列表，此处根据模板 ID 查询模板，再将模板中的规格列表（json 字符串）提取出来，转换成集合
     * 集合里的元素是 Map，用来保存对象
     */
    @Override
    public List<Map> findSpecList(Long id) {
        // 根据模板 ID 查询模板对象
        TbTypeTemplate tbTypeTemplate = tbTypeTemplateDao.queryById(id);

        // 提取模板对象中的规格列表属性（json字符串）
        String stringSpecIds = tbTypeTemplate.getSpecIds();

        // 将 json 字符串转换成集合，使用 fastJSON 工具
        // Map { id:"", text:"" }
        List<Map> specList = JSON.parseArray(stringSpecIds, Map.class);

        // 遍历 Map，根据属性 规格id 查询规格选项列表，将规格选项列表 put 到 map 中，作为 options 属性
        for (Map spec : specList) {
            // 获取属性 规格id
            Integer specId = (Integer)spec.get("id");
            // 查询规格选项列表
            List<TbSpecificationOption> specificationOptionList
                    = tbSpecificationOptionDao.queryBySpecId(new Long(specId));
            // 将规格选项列表 put 到 map 中
            spec.put("options", specificationOptionList);
        }

        // 返回规格列表集合 List<Map> Map { id:"", text:"", options:"" }
        return specList;
    }


    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbTypeTemplateDao tbTypeTemplateDao;


    @Autowired
    private TbSpecificationOptionDao tbSpecificationOptionDao;


    @Autowired
    private RedisTemplate redisTemplate;

}
