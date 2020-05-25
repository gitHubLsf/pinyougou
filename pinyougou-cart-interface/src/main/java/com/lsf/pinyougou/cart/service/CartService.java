package com.lsf.pinyougou.cart.service;


import com.lsf.pinyougou.pojogroup.Cart;
import java.util.List;


public interface CartService {


    /**
     * 添加商品 SKU 到购物车
     *
     * @param cartList  旧的购物车列表
     * @param itemId    商品 SKU ID
     * @param num       购买数量
     *
     * @return          新的购物车列表
     */
    List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);

}
