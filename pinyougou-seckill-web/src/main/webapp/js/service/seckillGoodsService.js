// 秒杀商品服务层
pyg.service('seckillGoodsService', function ($http) {

    // 查询正在参与秒杀的商品列表
    this.findList = function () {
        return $http.get('seckillGoods/findList.do');
    };


    // 查询某个正在参与秒杀的商品
    this.findOneFromCache = function (id) {
        return $http.get('seckillGoods/findOneFromCache.do?id=' + id);
    };
});