// 购物车控制层
pyg.controller('cartController', function ($scope,
                                           cartService,
                                           addressService,
                                           orderService) {

    // 查询购物车列表
    $scope.findCartList = function () {
        cartService.findCartList().success(
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
    $scope.order = {paymentType: '1'};

    // 修改支付方式
    $scope.selectPaymentType = function (type) {
        $scope.order.paymentType = type;
    };


    // 添加订单
    $scope.addOrder = function () {
        // 设置收件人信息
        $scope.order.receiverAreaName = $scope.chooseAddress.address;   // 地址
        $scope.order.receiverMobile = $scope.chooseAddress.mobile;  // 手机
        $scope.order.receiver = $scope.chooseAddress.contact;   // 联系人

        orderService.addOrder($scope.order).success(
            function (response) {
                if (response.success) {
                    // 添加订单成功，跳转到支付页面

                    if ($scope.order.paymentType === '1') { // 如果是在线支付
                        location.href = "pay.html";
                    } else if ($scope.order.paymentType === '2') {    // 如果是货到付款
                        alert('添加成功，货到付款');
                    } else {
                        alert('添加成功，但是支付类型异常');
                    }

                } else {
                    alert(response.message);
                }
            }
        );
    };

});	
