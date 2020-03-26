package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
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