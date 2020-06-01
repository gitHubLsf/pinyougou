// 购物车控制层
pyg.controller('cartController', function ($scope,
                                           cartService,
                                           addressService) {

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


    // 查询当前在线用户所有的地址
    $scope.findAddressListByUserName = function () {
        addressService.findAddressListByUserName().success(
            function (response) {
                $scope.addressList = response;

                // 查找默认地址
                for (var i = 0; i < $scope.addressList.length; i++) {
                    if ($scope.addressList[i].isDefault === '1') {
                        // $scope.chooseAddress 代表用户选中的地址，默认情况下是选中默认地址
                        $scope.chooseAddress = $scope.addressList[i];
                        break;
                    }
                }
            }
        );
    };


    // 修改用户选中的地址
    $scope.selectAddress = function (address) {
        $scope.chooseAddress = address;
    };


    // 添加地址
    $scope.addAddress = function () {
        addressService.addAddress($scope.address).success(
            function (response) {
                if (response.success) {
                    // 添加成功，重新查询当前在线用户所有的地址
                    $scope.findAddressListByUserName();
                } else {
                    alert(response.message);
                }
            }
        );
    };


    // 订单对象，对应后台的 TbOrder 类，数据库的 tb_order 表
    // 默认的支付方式 paymentType 为 '1'，表示在线支付，'2' 表示线下支付
    $scope.order = { paymentType:'1' };

    // 修改支付方式
    $scope.selectPaymentType = function (type) {
        $scope.order.paymentType = type;
    };


});	
