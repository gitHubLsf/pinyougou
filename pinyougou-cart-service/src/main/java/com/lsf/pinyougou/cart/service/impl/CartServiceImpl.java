package com.lsf.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lsf.pinyougou.cart.service.CartService;
import com.lsf.pinyougou.dao.TbItemDao;
import com.lsf.pinyougou.pojo.TbItem;
import com.lsf.pinyougou.pojo.TbOrderItem;
import com.lsf.pinyougou.pojogroup.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * 购物车服务实现层
 */
@Service
public class CartServiceImpl implements CartService {

    /**
     * 添加商品 SKU 到购物车
     *
     * @param cartList  旧的购物车列表
     * @param itemId    商品 SKU ID
     * @param num       购买数量
     *
     * @return          新的购物车列表
     */
    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        // 如果旧的 cartList 列表为 null，就创建一个空的列表给它
        if (cartList == null)
            cartList = new ArrayList<Cart>();

        // 1.根据商品 SKU ID itemId 查询商品 SKU 对象
        TbItem item = tbItemDao.queryById(itemId);
        if (item == null || !item.getStatus().equals("1"))
            throw new RuntimeException("商品不存在!");

        // 2.从查询到的商品 SKU 对象中获取商家 ID
        String sellerId = item.getSellerId();

        // 3.遍历旧的购物车列表 cartList，判断其中是否有商家 ID 对应的购物车对象 cart
        Cart cart = findCartFromCartListBySellerId(cartList, sellerId);

        // 4.如果 cart 存在
        if (cart != null) {
            // 4.1 遍历 cart 对象内的明细列表 orderItemList，判断其中是否有商品 SKU ID 对应的明细对象 orderItem
            TbOrderItem orderItem = findOrderItemFromOrderItemListByItemId(cart.getOrderItemList(), itemId);

            // 4.1.1 如果 orderItem 存在
            if (orderItem != null) {
                // 将 orderItem 的购买数量属性设置为 旧的购买数量 + num
                orderItem.setNum(orderItem.getNum() + num);

                // 如果购买数量被修改为 <= 0，那么就要从 orderItemList 中删除这个明细对象 orderItem
                if (orderItem.getNum() <= 0) {
                    cart.getOrderItemList().remove(orderItem);

                    // 如果明细列表 orderItemList 长度变为 0，就要从 cartList 列表中删除购物车对象 cart
                    if (cart.getOrderItemList().size() <= 0)
                        cartList.remove(cart);
                } else {
                    // 将 orderItem 的总额设置为新的购买数量 * 商品单价
                    orderItem.setTotalFee(orderItem.getPrice() * orderItem.getNum());
                }
            } else {
                // 4.1.2 如果 orderItem 不存在
                // 创建新的 orderItem 对象并添加到 orderItemList 明细列表中
                TbOrderItem newOrderItem = createOrderItem(item, num);
                cart.getOrderItemList().add(newOrderItem);
            }
        } else {
            // 5.如果 cart 不存在
            // 创建 cart 对象并添加到 cartList 列表中
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(item.getSeller());

            List<TbOrderItem> orderItemList = new ArrayList<>();
            TbOrderItem orderItem = createOrderItem(item, num);
            orderItemList.add(orderItem);

            cart.setOrderItemList(orderItemList);

            cartList.add(cart);
        }

        // 6.返回新的 cartList 列表
        return cartList;
    }


    /**
     * 创建新的明细对象 TbOrderItem 并返回
     */
    private TbOrderItem createOrderItem(TbItem item, Integer num) {
        if (num <= 0)
            throw new RuntimeException("购买数量非法");

        TbOrderItem newOrderItem = new TbOrderItem();

        newOrderItem.setGoodsId(item.getGoodsId());
        newOrderItem.setTitle(item.getTitle());
        newOrderItem.setItemId(item.getId());
        newOrderItem.setPicPath(item.getImage());
        newOrderItem.setNum(num);
        newOrderItem.setPrice(item.getPrice());
        newOrderItem.setTotalFee(num * item.getPrice());
        newOrderItem.setSellerId(item.getSellerId());

        return newOrderItem;
    }


    /**
     * 从明细列表 orderItemList 中查找对应商品 SKU ID itemId 的明细对象 TbOrderItem
     * 如果找到，则返回 TbOrderIte，否则返回 null
     */
    private TbOrderItem findOrderItemFromOrderItemListByItemId(List<TbOrderItem> orderItemList, Long itemId) {
        for (TbOrderItem orderItem : orderItemList) {
            if (orderItem.getItemId().longValue() == itemId.longValue()) {
                return orderItem;
            }
        }

        return null;
    }


    /**
     * 从购物车列表 cartList 中查询商家 ID sellerId 对应的购物车对象 Cart
     * 如果找到，返回 Cart 对象，否则返回 null
     */
    private Cart findCartFromCartListBySellerId(List<Cart> cartList, String sellerId) {
        for (Cart cart : cartList) {
            if (cart.getSellerId().equals(sellerId))
                return cart;
        }

        return null;
    }


    @Autowired
    private TbItemDao tbItemDao;
}
