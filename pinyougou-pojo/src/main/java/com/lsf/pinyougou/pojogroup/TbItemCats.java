package com.lsf.pinyougou.pojogroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 商品类目(TbItemCat)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbItemCats implements Serializable {
    private static final long serialVersionUID = 510323690483224434L;
    /**
    * 类目ID
    */
    private Long id;
    /**
    * 父类目ID=0时，代表的是一级的类目
    */
    private Long parentId;
    /**
    * 类目名称
    */
    private String name;
    /**
    * 类型id
    */
    private Long typeId;

    /**
     * 类型名称
     */
    private String typeName;
}