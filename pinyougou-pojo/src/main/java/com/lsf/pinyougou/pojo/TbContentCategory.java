package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 内容分类(TbContentCategory)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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