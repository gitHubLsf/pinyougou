package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 行政区域地州市信息表(TbCities)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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