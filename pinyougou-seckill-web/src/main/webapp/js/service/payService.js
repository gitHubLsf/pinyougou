// 支付服务层
pyg.service('payService', function ($http) {

    // 创建支付二维码
    this.createQRCode = function () {
        return $http.get('pay/createQRCode.do');
    };


    // 查询订单的支付状态
    this.queryPayStatus = function (outTradeNo) {
        return $http.get('pay/queryPayStatus.do?outTradeNo=' + outTradeNo);
    };

});
