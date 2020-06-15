package com.lsf.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsf.pinyougou.dao.*;
import com.lsf.pinyougou.pojo.*;
import com.lsf.pinyougou.pojogroup.Goods;
import com.lsf.pinyougou.pojogroup.TbItemCats;
import com.lsf.pinyougou.service.interfaces.sellergoods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vo.PageResult;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 商品服务
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return tbGoodsDao.queryAll(null);
    }


    /**
     * 多条件分页查询，逻辑上被删除的商品不查询
     */
    @Override
    public PageResult findPageLimit(TbGoods goods, int pageNum, int pageSize) {
        goods.setIsDelete("0");

        PageHelper.startPage(pageNum, pageSize);
        List<TbGoods> list = tbGoodsDao.queryAll(goods);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 添加商品
     */
    @Override
    @Transactional
    public void add(Goods goods) {
        // 设置商品的状态为 未审核
        goods.getTbGoods().setAuditStatus("0");

        // 设置商品的状态为上架，默认情况下，商品是上架的
        goods.getTbGoods().setIsMarketable("1");

        // 设置商品的状态为未删除，0 表示未删除，1 表示已删除
        goods.getTbGoods().setIsDelete("0");

        // 添加商品，同时返回商品 ID
        tbGoodsDao.insert(goods.getTbGoods());

        // 设置商品扩展的 ID 为商品 ID
        goods.getTbGoodsDesc().setGoodsId(goods.getTbGoods().getId());

        // 添加商品扩展
        tbGoodsDescDao.insert(goods.getTbGoodsDesc());

        // 添加商品 SKU 列表
        saveItemList(goods);
    }


    /**
     * 保存商品 SKU 列表
     */
    private void saveItemList(Goods goods) {
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
            String url = (String) imageList.get(0).get("url");
            item.setImage(url);
        }
    }


    /**
     * 商家修改自己的商品信息
     */
    @Override
    @Transactional
    public void update(Goods goods) {
        // 修改商品
        // 设置商品的状态为未审核，因为经过修改的商品需要重新审核
        goods.getTbGoods().setAuditStatus("0");
        tbGoodsDao.update(goods.getTbGoods());

        // 修改商品扩展
        tbGoodsDescDao.update(goods.getTbGoodsDesc());

        // 删除数据库中旧的 SKU 列表
        tbItemDao.deleteByGoodsId(goods.getTbGoods().getId());

        // 添加新的 SKU 列表
        saveItemList(goods);
    }


    /**
     * 根据商品 ID 查询商品信息，返回商品组合实体类对象 Goods
     */
    @Override
    public Goods findOne(long id) {
        Goods goods = new Goods();

        // 查询商品对象
        TbGoods tbGoods = tbGoodsDao.queryById(id);
        goods.setTbGoods(tbGoods);

        // 查询商品详情对象
        TbGoodsDesc tbGoodsDesc = tbGoodsDescDao.queryById(id);
        goods.setTbGoodsDesc(tbGoodsDesc);

        // 查询商品的 SKU 列表
        TbItem tbItem = new TbItem();
        tbItem.setGoodsId(id);
        List<TbItem> itemList = tbItemDao.queryAll(tbItem);
        goods.setTbItemList(itemList);

        return goods;
    }


    /**
     * 批量删除商品，只进行逻辑删除（将商品的 is_delete 字段设置为 "1"）
     * 同时批量删除商品 SKU，只进行逻辑删除，将 SKU 的 status 字段设置为 "3"
     */
    @Override
    public void batchDelete(Long[] ids) {
        // 批量删除商品 SPU
        tbGoodsDao.batchDeleteGoods(ids);

        // 批量编辑商品 SKU，修改 status 字段，因为此处是删除，所以 status 字段值为 "3"
        tbItemDao.batchEditItem(ids, "3");
    }


    /**
     * 批量修改商品的审核状态
     */
    @Override
    public void updateGoodStatus(Long[] ids, String status) throws Exception {
        tbGoodsDao.batchUpdateGoodsAuditStatus(ids, status);
    }


    /**
     * 修改商品的上下架状态，前提是商品已通过运营商审核
     */
    @Override
    public void updateGoodMarketable(Long[] ids, String status) {
        // 批量修改商品的上下架状态
        tbGoodsDao.batchUpdateGoodsMarketAble(ids, status);

        // 如果对商品的上下架状态进行修改
        // 那么对应的 SKU 信息的 status 状态也要修改
        String itemStatus = "3";
        if ("0".equals(status)) {
            itemStatus = "2";
        } else if ("1".equals(status)) {
            itemStatus = "1";
        }
        tbItemDao.batchEditItem(ids, itemStatus);
    }


    /**
     * 批量根据商品 SPU ID 查询商品 SKU，SKU 的状态 status 要等于 1
     */
    @Override
    public List<TbItem> batchSearchItemByGoodId(Long[] ids, String status) throws Exception {
        return tbItemDao.batchSearchItemByGoodId(ids, status);
    }


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
}
