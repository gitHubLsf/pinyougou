package com.lsf.pinyougou.pojogroup;

import com.lsf.pinyougou.pojo.TbSpecification;
import com.lsf.pinyougou.pojo.TbSpecificationOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 规格组合实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specification implements Serializable {

    private TbSpecification specification;

    private List<TbSpecificationOption> specificationOptionList;
}
