package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (TbSpecification)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbSpecification implements Serializable {
    private static final long serialVersionUID = 955017495072184258L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 名称
    */
    private String specName;

}