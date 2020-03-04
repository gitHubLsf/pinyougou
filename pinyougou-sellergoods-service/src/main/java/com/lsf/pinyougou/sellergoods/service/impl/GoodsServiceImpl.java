package com.lsf.pinyougou.sellergoods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.*;
import com.lsf.pinyougou.pojo.*;
import com.lsf.pinyougou.pojogroup.Goods;
import com.lsf.pinyougou.pojogroup.TbItemCats;
import com.lsf.pinyougou.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;

import vo.PageResult;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbGoodsDao tbGoodsDao;


    /**
     * 商品扩展数据访问层对象
     */
    @Autowired
    private TbGoodsDescDao tbGoodsDescDao;


    @Autowired
    private TbBrandDao tbBrandDao;


    @Autowired
    private TbItemCatDao tbItemCatDao;

    @Autowired
    private TbSellerDao tbSellerDao;

    @Autowired
    private TbItemDao tbItemDao;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return tbGoodsDao.queryAll(null);
    }


    /**
     * 无条件分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbGoods> list = tbGoodsDao.queryAll(null);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findPageLimit(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbGoods> list = tbGoodsDao.queryAll(goods);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加商品
     */
    @Override
    public void add(Goods goods) {

        // 设置商品的状态为 未审核
        goods.getTbGoods().setAuditStatus("0");

        // 添加商品，同时返回商品 ID
        tbGoodsDao.insert(goods.getTbGoods());

        // 设置商品扩展的 ID 为商品 ID
        goods.getTbGoodsDesc().setGoodsId(goods.getTbGoods().getId());

        // 添加商品扩展
        tbGoodsDescDao.insert(goods.getTbGoodsDesc());

        // 如果用户选择启用规格，则需要遍历 SKU 列表
        if ("1".equals(goods.getTbGoods().getIsEnableSpec())) {
            // 获取 SKU 列表,将每个 SKU 插入到数据库
            List<TbItem> itemList = goods.getTbItemList();
            for (TbItem item : itemList) {
                // 设置 SKU 的标题
                // 获取商品 SPU 名称
                String title = goods.getTbGoods().getGoodsName();
                // 获取 SKU 的 spec 属性 spec:{"网络制式":"", "存储容量":""}
                // 该属性在前端页面是对象，此处被转换为 json 字符串，我们需要使用 fastJSON 将它转换为对象
                Map<String, Object> spec = JSON.parseObject(item.getSpec());
                for (String key : spec.keySet()) {
                    title += " " + spec.get(key);
                }
                item.setTitle(title);

                // 设置其他共有的属性
                setItemValue(item, goods);

                // 添加 SKU 信息
                tbItemDao.insert(item);
            }
        } else {
            // 用户没有启用规格，则我们默认只添加一条 SKU 记录
            TbItem tbItem = new TbItem();
            // 设置 SKU 的标题，直接使用商品名称
            tbItem.setTitle(goods.getTbGoods().getGoodsName());
            // 设置价格为商品默认的价格
            tbItem.setPrice(goods.getTbGoods().getPrice());
            // 设置状态为 1
            tbItem.setStatus("1");
            // 设置为默认
            tbItem.setIsDefault("1");
            // 设置默认库存数量
            tbItem.setNum(10);
            // 设置规格对象为 {}
            tbItem.setSpec("{}");

            // 设置其他的共同属性
            setItemValue(tbItem, goods);

            // 添加 SKU 信息
            tbItemDao.insert(tbItem);
        }
    }

    private void setItemValue(TbItem item, Goods goods) {
        // 设置 SKU 所属的 SPU 编号，也就是商品的编号
        item.setGoodsId(goods.getTbGoods().getId());

        // 设置 SKU 所属的商家编号
        item.setSellerId(goods.getTbGoods().getSellerId());

        // 设置 SKU 的商品分类编号（此处设置 3 级分类）
        item.setCategoryid(goods.getTbGoods().getCategory3Id());

        // 设置 SKU 的创建日期
        item.setCreateTime(new Date());

        // 设置 SKU 的更新日期
        item.setUpdateTime(new Date());

        // 设置 SKU 的品牌名称
        // 根据品牌 ID 查询品牌名称
        TbBrand tbBrand = tbBrandDao.queryById(goods.getTbGoods().getBrandId());
        item.setBrand(tbBrand.getName());

        // 设置 SKU 的商品分类名称（此处设置 3 级分类名称）
        // 根据商品分类编号查询商品分类名称
        TbItemCats tbItemCats = tbItemCatDao.queryById(goods.getTbGoods().getCategory3Id());
        item.setCategory(tbItemCats.getName());

        // 设置 SKU 所属的商家名称
        // 根据商家 ID 查询商家名称
        TbSeller tbSeller = tbSellerDao.queryById(goods.getTbGoods().getSellerId());
        item.setSeller(tbSeller.getNickName());

        // 设置 SKU 图片地址，取商品图片列表集合中的第一张图片
        // 此处的图片列表集合是 json 字符串，我们将它转换成集合形式
        List<Map> imageList = JSON.parseArray(goods.getTbGoodsDesc().getItemImages(), Map.class);
        if (imageList.size() > 0) {
            // 表示有图片
            // 获取第一张图片的 url
            String url = (String)imageList.get(0).get("url");
            item.setImage(url);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(TbGoods goods) {
        tbGoodsDao.update(goods);
    }


    /**
     * 根据 ID 获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbGoods findOne(long id) {
        return tbGoodsDao.queryById(id);
    }


    /**
     * 批量删除
     */
    @Override
    public void batchDelete(Long[] ids) {
        for (Long id : ids) {
            tbGoodsDao.deleteById(id);
        }
    }

}
