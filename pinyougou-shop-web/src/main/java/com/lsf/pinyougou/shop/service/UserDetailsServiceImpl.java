package com.lsf.pinyougou.shop.service;

import com.lsf.pinyougou.pojo.TbSeller;
import com.lsf.pinyougou.service.interfaces.sellergoods.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证类，实现 UserDetailsService 接口，实现 loadUserByUsername 方法
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 这是商家管理服务层的接口，服务层对象在远程，本来需要使用 @Reference 注入，
     * 但是这里我们必须改用 setter 方法,在 spring-security 配置文件中，注入该对象
     */
    private SellerService sellerService;


    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 这个集合保存用户拥有的所有角色,这里的角色是模拟的，后续要到数据库中查询
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        // 到数据库中判断 username 用户是否存在
        TbSeller one = sellerService.findOne(username);
        if (one != null && "1".equals(one.getStatus())) {
            // 商家存在且商家的审核状态为已通过
            return new User(username, one.getPassword(), grantedAuths);
        } else {
            // 商家不存在或商家审核状态不正确
            return null;
        }
    }
}
