package com.lsf.pinyougou.cart.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lsf.pinyougou.pojogroup.Cart;
import com.lsf.pinyougou.service.interfaces.cart.CartService;
import com.lsf.pinyougou.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 购物车控制层
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    /**
     * 查询用户的购物车列表
     */
    @RequestMapping("/findCartList.do")
    public List<Cart> findCartList() {
        // 尝试获取登录用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        // 从 cookie 中查询购物车列表
        String stringCartList = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
        if (stringCartList == null || "".equals(stringCartList)) {
            stringCartList = "[]";
        }
        List<Cart> cookieCartList = JSON.parseArray(stringCartList, Cart.class);

        // 如果用户未登录
        if ("anonymousUser".equals(userName)) {
            // 直接返回 cookie 中查询到的购物车列表
            return cookieCartList;
        } else {
            // 用户已登录，从 redis 中查询购物车列表
            List<Cart> redisCartList = cartService.findCartListFromRedis(userName);

            // 判断 cookie 中的购物车列表是否有数据，如果有才需要进行合并
            // 如果没有，直接返回 redis 中查到的购物车列表
            if (cookieCartList.size() > 0) {
                // 合并 cookie 中的购物车和 redis 中的购物车
                List<Cart> mergeCartList = cartService.mergeCartList(cookieCartList, redisCartList);

                // 将合并后的购物车列表保存到 redis
                cartService.saveCartListToRedis(mergeCartList, userName);

                // 清空 cookie 中的购物车列表
                CookieUtil.deleteCookie(request, response, "cartList");

                // 返回合并后的购物车列表
                return mergeCartList;
            }

            return redisCartList;
        }
    }


    /**
     * 添加商品 SKU 到购物车列表
     *
     * @param itemId 商品 SKU ID
     * @param num    购买数量
     */
    @RequestMapping("/addGoodsToCartList.do")
    //@CrossOrigin(origins = "http://localhost:9105", allowCredentials = "true")
    //@CrossOrigin(origins = "http://localhost:9105")
    public Result addGoodsToCartList(Long itemId, Integer num) {

//        允许接收来自 http://localhost:9105 域（商品详情页前台）的跨域请求
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        如果设置为 *，表示允许接受来自任何域的跨域请求
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:9105");

        // 允许其他域的跨域请求携带本域设置的 cookie
        // 如果此方法涉及到跨域且需要操作 cookie，就必须写下面这句话
        // 同时前台的 AJAX 请求必须设置 {'withCredentials': true}
        // 但是如果设置了下面这句话，那么 response.setHeader("Access-Control-Allow-Origin", "*"); 就不能设置为 *
        response.setHeader("Access-Control-Allow-Credentials", "true");

        try {
            // 1.先查询购物车列表 cartList
            List<Cart> cartList = findCartList();

            // 2.调用服务层方法，实现商品 SKU 添加到购物车列表
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);

            // 尝试获取登录用户名
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();

            // 如果用户未登录
            if ("anonymousUser".equals(userName)) {
                // 将新的购物车列表 carList 写入 cookie 中
                saveCartListToCookie(cartList);
            } else {
                // 用户已登录，将新的购物车列表存入 redis 中
                cartService.saveCartListToRedis(cartList, userName);
            }

            return new Result(true, "添加购物车成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(false, "添加购物车失败");
        }
    }


    /**
     * 保存购物车列表 cartList 到 cookie 中
     */
    private void saveCartListToCookie(List<Cart> cartList) {
        CookieUtil.setCookie(
                request,
                response,
                "cartList",
                JSON.toJSONString(cartList),
                3600 * 24,
                "UTF-8");
    }


    /**
     * 防止调用超时，此处设置时间限制为 6 秒，默认是 1 秒
     */
    @Reference(timeout = 6000)
    private CartService cartService;


    @Autowired
    private HttpServletRequest request;


    @Autowired
    private HttpServletResponse response;
}
