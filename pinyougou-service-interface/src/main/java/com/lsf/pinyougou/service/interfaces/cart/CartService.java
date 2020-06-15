package com.lsf.pinyougou.service.interfaces.cart;

import com.lsf.pinyougou.pojogroup.Cart;
import java.util.List;


public interface CartService {

    /**
     * 添加商品 SKU 到购物车列表 cartList
     * 这是通用方法，适用与 cookie 存储购物车和 redis 存储购物车
     *
     * @param cartList  旧的购物车列表
     * @param itemId    商品 SKU ID
     * @param num       购买数量
     *
     * @return          新的购物车列表
     */
    List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);


    /**
     * 从 redis 中读取用户 userName 的购物车列表
     *
     * @param userName  用户名
     */
    List<Cart> findCartListFromRedis(String userName);


    /**
     * 将购物车列表 cartList 保存到 redis 中
     *
     * @param cartList  购物车列表
     * @param userName  用户名
     */
    void saveCartListToRedis(List<Cart> cartList, String userName);


    /**
     * 合并两个购物车列表，并返回合并后的新购物车列表
     */
    List<Cart> mergeCartList(List<Cart> cartList_1, List<Cart> cartList_2);

}
