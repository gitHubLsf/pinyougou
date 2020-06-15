package com.lsf.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsf.pinyougou.pojo.TbUser;
import com.lsf.pinyougou.service.interfaces.user.UserService;
import com.lsf.pinyougou.util.PhoneFormatCheckUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.PageResult;
import vo.Result;

import java.util.List;


/**
 * 用户中心前台控制层
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;


    /**
     * 返回全部列表
     */
    @RequestMapping("/findAll.do")
    public List<TbUser> findAll() {
        return userService.findAll();
    }



    /**
     * 多条件分页查询
     */
    @RequestMapping("/search.do")
    public PageResult findPageLimit(@RequestBody TbUser user, int page, int size) {
        return userService.findPageLimit(user, page, size);
    }


    /**
     * 用户注册
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbUser user, String smsCode) {
        // 判断验证码是否正确
        if (!userService.checkSmsCode(user.getPhone(), smsCode)) {
            return new Result(false, "验证码错误");
        }

        try {
            userService.add(user);
            return new Result(true, "注册成功");
        } catch (Exception e) {
            return new Result(false, "注册失败");
        }
    }


    /**
     * 修改
     */
    @RequestMapping("/update.do")
    public Result update(@RequestBody TbUser user) {
        try {
            userService.update(user);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            return new Result(false, "修改失败");
        }
    }


    /**
     * 根据 ID 获取实体
     */
    @RequestMapping("/findOne.do")
    public TbUser findOne(Long id) {
        return userService.findOne(id);
    }


    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete.do")
    public Result batchDelete(Long[] ids) {
        try {
            userService.batchDelete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }


    /**
     * 发送短信验证码
     */
    @RequestMapping("/sendSmsCode.do")
    public Result sendSmsCode(String phone) {
        // 判断手机号格式是否正确，且为中国大陆号码
        if (!PhoneFormatCheckUtils.isPhoneLegal(phone)) {
            return new Result(false, "手机号格式不正确");
        }

        try {
            userService.createSmsCode(phone);
            return new Result(true, "验证码发送成功");
        } catch (Exception e) {
            return new Result(false, "验证码发送失败");
        }
    }

}
