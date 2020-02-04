package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (TbBrand)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbBrand implements Serializable {
    private static final long serialVersionUID = -73907644997683792L;
    
    private Long id;
    /**
    * 品牌名称
    */
    private String name;
    /**
    * 品牌首字母
    */
    private String firstChar;

}