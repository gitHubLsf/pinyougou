package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (TbTypeTemplate)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbTypeTemplate implements Serializable {
    private static final long serialVersionUID = -95697953369333430L;
    
    private Long id;
    /**
    * 模板名称
    */
    private String name;
    /**
    * 关联规格
    */
    private String specIds;
    /**
    * 关联品牌
    */
    private String brandIds;
    /**
    * 自定义属性
    */
    private String customAttributeItems;

}