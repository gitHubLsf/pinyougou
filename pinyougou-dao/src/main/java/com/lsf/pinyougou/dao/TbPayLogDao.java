package com.lsf.pinyougou.dao;


import com.lsf.pinyougou.pojo.TbPayLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TbPayLogDao {


    TbPayLog queryById(String outTradeNo);


    List<TbPayLog> queryAll(TbPayLog tbPayLog);


    int insert(TbPayLog tbPayLog);


    int update(TbPayLog tbPayLog);


    int deleteById(String outTradeNo);

}