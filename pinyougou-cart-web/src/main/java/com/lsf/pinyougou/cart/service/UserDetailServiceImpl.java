package com.lsf.pinyougou.cart.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义认证类
 * 在没有集成 CAS,单独使用 spring security 时，该类的作用是到数据库查询用户信息和添加角色
 *
 * 在集成 CAS 后，由于 CAS 服务端已经有数据库验证用户信息功能
 * 那么这个类的主要作用是根据用户名查询拥有的角色或执行一些逻辑
 */
public class UserDetailServiceImpl implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 构建角色集合
        // 在电商系统中，角色比较简单，所以此处只是模拟出一个角色
        // 在传统项目中，角色丰富，此处需要到数据库中查询用户拥有的所有角色
        List<GrantedAuthority> authorities=new ArrayList();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // 此处模拟一个通过认证的角色，实际的认证工作在 CAS 服务端进行，此处主要作用是返回角色列表
        return new User(username, ""  , authorities);
    }
}
