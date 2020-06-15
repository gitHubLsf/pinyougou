package com.lsf.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbItemCatDao;
import com.lsf.pinyougou.pojo.TbItemCat;
import com.lsf.pinyougou.pojogroup.TbItemCats;
import com.lsf.pinyougou.service.interfaces.sellergoods.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import vo.PageResult;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * 商品分类服务实现层
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @PostConstruct
    private void init() {
        // 当对象创建时，提前往 redis 中缓存 k=分类名称，v=模板ID
        if (tbItemCatDao != null && redisTemplate != null) {
            List<TbItemCat> list = tbItemCatDao.queryAll(new TbItemCat());
            for (TbItemCat t : list) {
                redisTemplate.boundHashOps("catNameToTypeID").put(t.getName(), t.getTypeId());
            }
        }
    }


    /**
     * 查询全部
     */
    @Override
    public List<TbItemCat> findAll() {
        return tbItemCatDao.queryAll(null);
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbItemCat itemCat, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbItemCat> list = tbItemCatDao.queryAll(itemCat);
        PageInfo<TbItemCat> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加商品分类（数据库中添加，缓存中也添加）
     */
    @Override
    public void add(TbItemCat itemCat) {
        // 往数据库中添加商品分类
        tbItemCatDao.insert(itemCat);

        // 往 catNameToTypeID map 缓存中添加 k=分类名称，v=模板ID
        redisTemplate.boundHashOps("catNameToTypeID").put(itemCat.getName(), itemCat.getTypeId());
    }


    /**
     * 修改（修改数据库中的内容，删除缓存中旧的商品分类名称）
     */
    @Override
    public void update(TbItemCat itemCat) {

        // 查找数据库中旧的商品分类的名称
        TbItemCat tbItemCat = new TbItemCat();
        tbItemCat.setId(itemCat.getId());
        List<TbItemCat> result = tbItemCatDao.queryAll(tbItemCat);

        if (result != null && result.size() > 0) {
            // 修改数据库中的商品分类
            tbItemCatDao.update(itemCat);

            // 获取旧的商品分类名称
            String oldName = result.get(0).getName();

            if (!oldName.equals(itemCat.getName())) {
                // 商品分类名称变了
                // 删除 catNameToTypeID map 缓存中 k=旧的商品分类名称
                redisTemplate.boundHashOps("catNameToTypeID").delete(oldName);
                // 将新的商品分类名称缓存
                redisTemplate.boundHashOps("catNameToTypeID").put(itemCat.getName(), itemCat.getTypeId());
            } else {
                // 商品分类名称没变
                // 更新缓存数据
                redisTemplate.boundHashOps("catNameToTypeID").put(itemCat.getName(), itemCat.getTypeId());
            }
        }
    }


    /**
     * 根据 ID 获取实体
     */
    @Override
    public TbItemCats findOne(long id) {
        return tbItemCatDao.queryById(id);
    }


    /**
     * 批量删除商品分类（删除数据库中的内容，删除缓存中的内容）
     */
    @Override
    public void batchDelete(Long[] ids) throws Exception {
        for (Long id : ids) {
            // 判断分类 ID 下是否还有分类，有的话不能删除
            TbItemCat tbItemCat = new TbItemCat();
            tbItemCat.setParentId(id);
            List<TbItemCat> tbItemCats = tbItemCatDao.queryAll(tbItemCat);
            if (tbItemCats != null && tbItemCats.size() > 0) {
                throw new Exception();
            } else {
                // 获取商品分类的名称
                TbItemCat tb = new TbItemCat();
                tb.setId(id);
                List<TbItemCat> result = tbItemCatDao.queryAll(tb);
                if (result != null && result.size() > 0) {
                    String name = result.get(0).getName();

                    // 删除数据库中的商品分类
                    tbItemCatDao.deleteById(id);

                    // 删除 catNameToTypeID map 缓存中 k=旧的商品分类名称
                    redisTemplate.boundHashOps("catNameToTypeID").delete(name);
                }
            }
        }
    }


    /**
     * 根据上级 ID 查询商品分类
     */
    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
        TbItemCat tbItemCat = new TbItemCat();
        tbItemCat.setParentId(parentId);
        return tbItemCatDao.queryAll(tbItemCat);
    }


    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbItemCatDao tbItemCatDao;


    @Autowired
    private RedisTemplate redisTemplate;
}
