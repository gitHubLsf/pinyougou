// 服务层
pyg.service('goodsService', function ($http) {

    // 查询所有数据
    this.findAll = function () {
        return $http.get('../goods/findAll.do');
    };

    // 无条件分页查询
    this.findPage = function (page, size) {
        return $http.get('../goods/findPage.do?page=' + page + '&size=' + size);
    };

    // 根据 ID 查询实体
    this.findOne = function (id) {
        return $http.get('../goods/findOne.do?id=' + id);
    };

    // 添加商品
    this.add = function (entity) {
        return $http.post('../goods/add.do', entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.post('../goods/update.do', entity);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('../goods/batcheDelete.do?ids=' + selectedList);
    };

    // 多条件分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../goods/search.do?page=' + page + "&size=" + size, searchEntity);
    };
});
