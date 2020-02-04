package com.lsf.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lsf.pinyougou.dao.TbBrandDao;
import com.lsf.pinyougou.pojo.TbBrand;
import com.lsf.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 品牌列表服务实现
 *
 * @author linshaofeng
 * @date 2020/2/4 13:02
 */
@Service
public class BrandServiceImpl implements BrandService {

    /**
     * 此处依赖的 dao 对象是本地调用,使用本地依赖注入即可
     */
    @Autowired
    private TbBrandDao tbBrandDao;

    @Override
    public List<TbBrand> findAll() {
        return tbBrandDao.queryAll(new TbBrand());
    }
}
