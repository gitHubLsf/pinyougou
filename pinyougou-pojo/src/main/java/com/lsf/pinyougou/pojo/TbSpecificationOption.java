package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (TbSpecificationOption)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbSpecificationOption implements Serializable {
    private static final long serialVersionUID = -89253780381220836L;
    /**
    * 规格项ID
    */
    private Long id;
    /**
    * 规格项名称
    */
    private String optionName;
    /**
    * 规格ID
    */
    private Long specId;
    /**
    * 排序值
    */
    private Integer orders;

}