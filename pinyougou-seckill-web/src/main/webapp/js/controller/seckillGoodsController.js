// 秒杀商品控制层
pyg.controller('seckillGoodsController', function ($scope, seckillGoodsService) {

    // 查询正在参与秒杀的商品列表
    $scope.findList = function () {
        seckillGoodsService.findList().success(
            function (response) {
                $scope.list = response;
            }
        );
    };
});