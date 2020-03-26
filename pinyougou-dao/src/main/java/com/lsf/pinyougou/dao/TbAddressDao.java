package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbAddress;

import java.util.List;


public interface TbAddressDao {

    TbAddress queryById(Long id);


    List<TbAddress> queryAll(TbAddress tbAddress);


    int insert(TbAddress tbAddress);


    int update(TbAddress tbAddress);


    int deleteById(Long id);

}