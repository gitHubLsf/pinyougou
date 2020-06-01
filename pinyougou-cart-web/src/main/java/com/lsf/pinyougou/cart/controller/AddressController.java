package com.lsf.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbAddress;
import com.lsf.pinyougou.user.service.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.Result;
import java.util.Date;
import java.util.List;


/**
 * 地址管理控制层
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    /**
     * 查询当前在线用户的所有地址
     */
    @RequestMapping("/findAddressListByUserName.do")
    public List<TbAddress> findAddressListByUserName() {
        // 当前登录用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        return addressService.findAddressListByUserId(userName);
    }


    /**
     * 添加地址
     */
    @RequestMapping("/addAddress.do")
    public Result addAddress(@RequestBody TbAddress address) {
        try {
            // 当前登录用户名
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();

            address.setUserId(userName);
            address.setCreateDate(new Date());
            address.setIsDefault("0");
            addressService.add(address);

            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }


    @Reference
    private AddressService addressService;
}
