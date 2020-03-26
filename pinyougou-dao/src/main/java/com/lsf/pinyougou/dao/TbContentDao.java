package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbContent;

import java.util.List;


public interface TbContentDao {


    TbContent queryById(Long id);


    List<TbContent> queryAll(TbContent tbContent);


    int insert(TbContent tbContent);


    int update(TbContent tbContent);


    int deleteById(Long id);

}