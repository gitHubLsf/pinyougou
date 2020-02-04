package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 行政区域县区信息表(TbAreas)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbAreas implements Serializable {
    private static final long serialVersionUID = 204660681857858644L;
    /**
    * 唯一ID
    */
    private Integer id;
    /**
    * 区域ID
    */
    private String areaid;
    /**
    * 区域名称
    */
    private String area;
    /**
    * 城市ID
    */
    private String cityid;


}