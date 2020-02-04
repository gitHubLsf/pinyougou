package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 省份信息表(TbProvinces)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbProvinces implements Serializable {
    private static final long serialVersionUID = -95081730079644852L;
    /**
    * 唯一ID
    */
    private Integer id;
    /**
    * 省份ID
    */
    private String provinceid;
    /**
    * 省份名称
    */
    private String province;

}