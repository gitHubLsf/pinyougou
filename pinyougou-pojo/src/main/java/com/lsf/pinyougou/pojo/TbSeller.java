package com.lsf.pinyougou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (TbSeller)实体类
 *
 * @author makejava
 * @since 2020-02-03 17:35:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbSeller implements Serializable {
    private static final long serialVersionUID = -84446733279122042L;

    /**
    * 用户ID
    */
    private String sellerId;

    /**
     * 密码
     */
    private String password;

    /**
     * 店铺名称
     */
    private String nickName;

    /**
    * 公司名
    */
    private String name;

    /**
     * 公司电话
     */
    private String telephone;

    /**
     * 公司详细地址
     */
    private String addressDetail;

    /**
     * 联系人姓名
     */
    private String linkmanName;

    /**
     * 联系人QQ
     */
    private String linkmanQq;

    /**
     * 联系人手机
     */
    private String linkmanMobile;

    /**
     * 联系人 EMAIL
     */
    private String linkmanEmail;

    /**
     * 营业执照号
     */
    private String licenseNumber;

    /**
     * 税务登记证号
     */
    private String taxNumber;

    /**
     * 组织机构代码证
     */
    private String orgNumber;

    /**
     * 法定代表人
     */
    private String legalPerson;

    /**
     * 法定代表人身份证
     */
    private String legalPersonCardId;

    /**
     * 开户行账号名称
     */
    private String bankUser;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建日期
     */
    private Date createTime;


    /**
    * EMAIL
    */
    private String email;

    /**
    * 公司手机
    */
    private String mobile;

    /**
    * 公司地址
    */
    private Long address;

    /**
    * 公司LOGO图
    */
    private String logoPic;

    /**
    * 简介
    */
    private String brief;

}