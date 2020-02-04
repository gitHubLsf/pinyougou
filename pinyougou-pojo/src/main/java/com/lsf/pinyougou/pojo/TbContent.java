package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (TbContent)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbContent implements Serializable {
    private static final long serialVersionUID = -99255229395867080L;
    
    private Long id;
    /**
    * 内容类目ID
    */
    private Long categoryId;
    /**
    * 内容标题
    */
    private String title;
    /**
    * 链接
    */
    private String url;
    /**
    * 图片绝对路径
    */
    private String pic;
    /**
    * 状态
    */
    private String status;
    /**
    * 排序
    */
    private Integer sortOrder;

}