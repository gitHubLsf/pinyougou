package com.lsf.pinyougou.cart.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lsf.pinyougou.cart.service.CartService;
import com.lsf.pinyougou.pojogroup.Cart;
import com.lsf.pinyougou.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 从 cookie 中提取购物车列表 cartList
     */
    @RequestMapping("/findCartListFromCookie.do")
    public List<Cart> findCartListFromCookie() {
        String stringCartList = CookieUtil.getCookieValue(request, "cartList", "UTF-8");

        if (stringCartList == null || "".equals(stringCartList))
            stringCartList = "[]";

        List<Cart> cartList = JSON.parseArray(stringCartList, Cart.class);

        return cartList;
    }


    /**
     * 添加商品 SKU 到购物车
     *
     * @param itemId 商品 SKU ID
     * @param num    购买数量
     */
    @RequestMapping("/addGoodsToCartList.do")
    public Result addGoodsToCartList(Long itemId, Integer num) {
        try {
            // 1.从 cookie 中获取购物车列表 cartList
            List<Cart> cartList = findCartListFromCookie();

            // 2.调用服务层方法，实现商品 SKU 添加到购物车
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);

            // 3.将新的购物车列表 carList 写入 cookie 中
            saveCartListToCookie(cartList);

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


    @Reference
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

}
