package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbUser;

import java.util.List;


public interface TbUserDao {


    TbUser queryById(Long id);


    List<TbUser> queryAll(TbUser tbUser);


    int insert(TbUser tbUser);


    int update(TbUser tbUser);


    int deleteById(Long id);

}