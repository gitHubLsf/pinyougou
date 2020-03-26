package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbCities implements Serializable {

    private static final long serialVersionUID = 865089318151237492L;

    /**
     * 唯一ID
     */
    private Integer id;

    /**
     * 城市ID
     */
    private String cityid;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 省份ID
     */
    private String provinceid;

}