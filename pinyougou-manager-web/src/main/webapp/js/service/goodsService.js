// 服务层
pyg.service('goodsService', function ($http) {

    // 根据 ID 查询实体
    this.findOne = function (id) {
        return $http.get('../goods/findOne.do?id=' + id);
    };

    // 多条件分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../goods/search.do?page=' + page + "&size=" + size, searchEntity);
    };

    // 运营商批量修改商品的审核状态
    this.updateGoodStatus = function (ids, status) {
        return $http.get('../goods/updateGoodStatus.do?ids='
            + ids
            + "&status="
            + status);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('../goods/batchDelete.do?ids=' + selectedList);
    };
});
