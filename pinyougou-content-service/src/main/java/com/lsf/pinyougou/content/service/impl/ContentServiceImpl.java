package com.lsf.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.TbContentDao;
import com.lsf.pinyougou.pojo.TbContent;
import com.lsf.pinyougou.service.interfaces.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import vo.PageResult;

import java.util.List;


/**
 * 广告服务实现层
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Override
    public List<TbContent> findAll() {
        return tbContentDao.queryAll(null);
    }


    @Override
    public PageResult findPageLimit(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbContent> list = tbContentDao.queryAll(content);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加广告（往数据库中添加广告成功后，随后要将缓存中对应广告的广告分类集合清空
     * 方便后续查询到新的数据）
     */
    @Override
    public void add(TbContent content) {
        // 往数据库中添加广告
        tbContentDao.insert(content);

        // 清空对应广告的广告分类集合
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }


    /**
     * 更新广告
     * 1.获取原始广告的广告分类ID
     * 2.将新的广告更新到数据库中
     * 3.删除缓存中，旧的广告分类集合
     * 4.如果新的广告分类 ID 和旧的广告分类 ID 不一致，就删除缓存中，新的广告分类集合
     */
    @Override
    public void update(TbContent content) {
        // 获取旧的广告分类ID
        TbContent oldContent = tbContentDao.queryById(content.getId());
        Long oldCategoryId = oldContent.getCategoryId();

        // 将新的广告更新到数据库
        tbContentDao.update(content);

        // 删除缓存中，旧的广告分类集合
        redisTemplate.boundHashOps("content").delete(oldCategoryId);

        // 获取新的广告分类 ID
        Long newCategoryId = content.getCategoryId();
        if (oldCategoryId.longValue() != newCategoryId.longValue()) {
            // 旧的广告分类和新的广告分类不一致
            // 删除缓冲中，新的广告分类集合
            redisTemplate.boundHashOps("content").delete(newCategoryId);
        }
    }


    @Override
    public TbContent findOne(long id) {
        return tbContentDao.queryById(id);
    }


    /**
     * 批量删除广告，每次删除一个广告后，要将缓存中，对应广告分类集合删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        if (ids != null && ids.length > 0) {
            // 查询这些广告对应的广告分类集合
            List<Long> categoryIdList = tbContentDao.findContentsCategoryList(ids);

            // 根据广告 ID 批量删除广告
            tbContentDao.batchDeleteContent(ids);

            // 遍历广告分类集合，删除缓存中对应的广告分类列表
            for (Long categoryId : categoryIdList) {
                redisTemplate.boundHashOps("content").delete(categoryId);
            }
        }
    }


    /**
     * 查询指定广告分类 ID 下的所有广告，先查缓存，缓存没有再查数据库
     */
    @Override
    public List<TbContent> findByContentCategoryId(Long contentCategoryId) {
        // 先查找缓存
        List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps("content").get(contentCategoryId);

        if (null == list) {
            // 缓存没找到，去数据库查找
            TbContent content = new TbContent();
            content.setCategoryId(contentCategoryId);
            // 只显示有效的广告
            content.setStatus("1");
            list = tbContentDao.findByContentCategoryId(content);
            // 放入缓存中
            redisTemplate.boundHashOps("content").put(contentCategoryId, list);
        }

        // 返回结果
        return list;
    }


    @Autowired
    private TbContentDao tbContentDao;


    /**
     * 数据库缓存
     */
    @Autowired
    private RedisTemplate redisTemplate;

}
