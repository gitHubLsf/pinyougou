package com.lsf.pinyougou.dao;

import com.lsf.pinyougou.pojo.TbSpecificationOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TbSpecificationOptionDao {


    TbSpecificationOption queryById(Long id);


    List<TbSpecificationOption> queryAll(TbSpecificationOption tbSpecificationOption);


    int insert(TbSpecificationOption tbSpecificationOption);


    int update(TbSpecificationOption tbSpecificationOption);


    int deleteById(Long id);


    /**
     * 查询某种规格 ID 包含的全部规格选项
     *
     * @param id
     * @return
     */
    List<TbSpecificationOption> queryBySpecId(Long id);


    /**
     * 根据规格 ID 删除规格选项
     *
     * @param id
     */
    void deleteBySpecId(Long id);


    /**
     * 根据规格 ID 批量删除规格选项
     */
    int batchDeleteBySpecId(@Param("ids") Long[] ids);

}