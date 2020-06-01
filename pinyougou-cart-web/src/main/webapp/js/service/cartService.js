// 购物车服务层
pyg.service('cartService', function ($http) {

    // 查询 cookie 中存储的购物车列表
    this.findCartListFromCookie = function () {
        return $http.get('cart/findCartList.do');
    };


    // 添加商品 SKU 到购物车
    this.addGoodsToCartList = function (itemId, num) {
        return $http.get('cart/addGoodsToCartList.do?itemId=' + itemId + '&num=' + num);
    };


    // 计算商品总数量和总价
    this.countTotalValue = function (cartList) {
        var totalValue = { 'totalNum': 0, 'totalPrice': 0.00 };

        for (var i = 0; i < cartList.length; i++) {
            var cart = cartList[i];
            for (var j = 0; j < cart.orderItemList.length; j++) {
                var orderItem = cart.orderItemList[j];
                totalValue.totalNum += orderItem.num;
                totalValue.totalPrice += orderItem.totalFee;
            }
        }

        return totalValue;
    };


    // 查询当前在线用户所有的地址
    this.findAddressListByUserName = function () {
        return $http.get('address/findAddressListByUserName.do');
    };


    // 添加地址
    this.addAddress = function (address) {
        return $http.post('address/addAddress.do', address);
    }

});