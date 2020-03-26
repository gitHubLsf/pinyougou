package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
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