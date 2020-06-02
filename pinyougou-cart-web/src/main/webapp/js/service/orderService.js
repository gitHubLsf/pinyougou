// 订单管理服务层
pyg.service('orderService', function ($http) {

    // 添加订单
    this.addOrder = function (order) {
        return $http.post('order/add.do', order);
    };

});
