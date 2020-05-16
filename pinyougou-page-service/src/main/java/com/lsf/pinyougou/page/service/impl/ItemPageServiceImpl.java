package com.lsf.pinyougou.page.service.impl;

import com.lsf.pinyougou.dao.TbGoodsDao;
import com.lsf.pinyougou.dao.TbGoodsDescDao;
import com.lsf.pinyougou.dao.TbItemCatDao;
import com.lsf.pinyougou.dao.TbItemDao;
import com.lsf.pinyougou.page.service.ItemPageService;
import com.lsf.pinyougou.pojo.TbGoods;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.pojogroup.TbItemCats;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品详情页服务实现层
 * 使用 spring 自带的 @Service 注解
 */
@Service
public class ItemPageServiceImpl implements ItemPageService {

    /**
     * 批量根据商品 SPU ID 生成商品详情页
     */
    @Override
    public void getItemHtml(Long[] ids) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer writer = null;

        for (Long id : ids) {
            try {
                // 模板
                Template template = configuration.getTemplate("item.ftl");

                // 数据模型
                Map<String, Object> map = new HashMap<>();

                // 查询商品
                TbGoods goods = goodsDao.queryById(id);
                map.put("goods", goods);

                // 查询商品详情
                map.put("goodsDesc", goodsDescDao.queryById(id));

                // 查询商品所属的各级分类的名称
                TbItemCats tbItemCats1 = itemCatDao.queryById(goods.getCategory1Id());
                if (tbItemCats1 != null)
                    map.put("itemCat1", tbItemCats1.getName());
                TbItemCats tbItemCats2 = itemCatDao.queryById(goods.getCategory2Id());
                if (tbItemCats1 != null)
                    map.put("itemCat2", tbItemCats2.getName());
                TbItemCats tbItemCats3 = itemCatDao.queryById(goods.getCategory3Id());
                if (tbItemCats1 != null)
                    map.put("itemCat3", tbItemCats3.getName());

                // 查询商品对应的 SKU 列表
                TbItem item = new TbItem();
                item.setGoodsId(id);
                item.setStatus("1");    // SKU 的状态必须为正常
                // 按照 SKU 是否默认（is_default） 字段降序排列，保证查询结果的第一条记录为默认 SKU
                List<TbItem> itemList = itemDao.queryAllSortByIsDefault(item);
                map.put("itemList", itemList);

                // 输出对象
                writer = new PrintWriter(pageDir + id + ".html");
                template.process(map, writer);
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 批量根据商品 SPU ID 删除生成的商品详情静态页
     */
    @Override
    public void deleteItemHtml(Long[] ids) {
        for (Long id : ids) {
            File file = new File(pageDir + id + ".html");
            if (file.exists() && file.isFile()) {
                // 文件对象存在，且是文件而不是目录
                file.delete();
            }
        }
    }


    /**
     * 目标文件的存储位置
     */
    @Value("${pageDir}")
    private String pageDir;


    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @Autowired
    private TbGoodsDao goodsDao;


    @Autowired
    private TbGoodsDescDao goodsDescDao;


    @Autowired
    private TbItemCatDao itemCatDao;


    @Autowired
    private TbItemDao itemDao;
}
