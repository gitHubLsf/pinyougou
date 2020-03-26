package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbContentCategory implements Serializable {

    private static final long serialVersionUID = -18434789714330990L;

    /**
     * 类目ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

}