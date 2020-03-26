package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
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