package com.lsf.pinyougou.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 登录逻辑相关
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 获取当前登录的用户名
     */
    @RequestMapping("/getLoginName.do")
    public Map getLoginName() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map = new HashMap<>();
        map.put("loginName", name);
        return map;
    }
}
