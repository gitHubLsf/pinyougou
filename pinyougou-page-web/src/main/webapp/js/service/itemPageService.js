pyg.service('itemPageService', function ($http) {

    // 添加商品到购物车
    this.addGoodsToCartList = function (itemId, num) {
        return $http.get('http://localhost:9107/cart/addGoodsToCartList.do?itemId=' + itemId + '&num=' + num,
            {'withCredentials': true});
        // {'withCredentials': true} 表示允许携带 cookie 到服务器和处理服务器回送的 cookie
        // 需要服务器设置 response.setHeader("Access-Control-Allow-Credentials", "true");
    };

});