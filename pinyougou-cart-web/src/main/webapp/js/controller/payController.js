// 支付控制层
pyg.controller('payController', function ($scope,
                                          payService,
                                          $location) {

    // 创建支付二维码
    $scope.createQRCode = function () {
        payService.createQRCode().success(
            function (response) {
                if (response == null) {
                    alert("系统异常");
                    return;
                }

                // 获取支付订单号
                $scope.outTradeNo = response.outTradeNo;

                // 获取支付总金额（将返回的分转换成元，并保留2位小数）
                $scope.totalFee = (response.totalFee / 100).toFixed(2);

                // 生成二维码
                var qrCode = new QRious({
                    element: document.getElementById('qrious'),
                    size: 300,	// 尺寸
                    background: "white",	// 背景色
                    foreground: "black",	// 前景色
                    level: "H",			// 容错级别
                    value: response.codeUrl    // 返回的二维码地址
                });

                // 生成二维码成功后，就开始查询订单的支付状态
                queryPayStatus($scope.outTradeNo);
            }
        );
    };


    // 查询订单的支付状态
    queryPayStatus = function (outTradeNo) {
        payService.queryPayStatus(outTradeNo).success(
            function (response) {
                if (response.success) {
                    // 支付成功
                    // 跳转到支付成功页面，并携带支付总金额路由过去
                    location.href = 'paysuccess.html#?totalFee=' + $scope.totalFee;
                } else {
                    // 可能是支付失败，可能是支付时间超时
                    if (response.message === 'timeout') {
                        // 如果是支付时间超时，就更新验证码
                        $scope.createQRCode();
                    } else {
                        // 支付失败了
                        location.href = 'payfail.html';
                    }
                }
            }
        );
    };


    // 支付成功页面 paysuccess.html 获取支付成功的总金额
    $scope.getTotalFee = function () {
        return $location.search()['totalFee'];
    }
});	
