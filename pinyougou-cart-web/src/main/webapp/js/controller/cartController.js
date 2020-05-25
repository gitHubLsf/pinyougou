// 购物车控制层
pyg.controller('cartController', function ($scope,
                                           cartService) {

    // 从 cookie 中查询购物车列表
    $scope.findCartListFromCookie = function () {
        cartService.findCartListFromCookie().success(
            function (response) {
                $scope.cartList = response;
                $scope.totalValue = cartService.countTotalValue($scope.cartList);   // 计算商品总数量和总价
            }
        );
    };


    // 添加商品 SKU 到购物车
    $scope.addGoodsToCartList = function (itemId, num) {
        cartService.addGoodsToCartList(itemId, num).success(
            function (response) {
                if (response.success) {
                    $scope.findCartListFromCookie();	// 刷新购物车列表
                    $scope.totalValue = cartService.countTotalValue($scope.cartList);   // 计算商品总数量和总价
                } else {
                    alert(response.message);//弹出错误提示
                }
            }
        );
    };

});	
