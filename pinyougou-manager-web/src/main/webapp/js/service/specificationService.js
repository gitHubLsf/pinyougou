// 规格服务层
pyg.service('specificationService', function ($http) {

    // 查询所有规格数据
    this.findAll = function () {
        return $http.get('../specification/findAll.do');
    };

    // 无条件分页查询所有规格数据
    this.findPage = function (page, size) {
        return $http.get('../specification/findPage.do?page=' + page + '&size=' + size);
    };

    // 根据 ID 查询实体
    this.findOne = function (id) {
        return $http.get('../specification/findOne.do?id=' + id);
    };

    // 增加
    this.add = function (entity) {
        return $http.post('../specification/add.do', entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.post('../specification/update.do', entity);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('../specification/batchDelete.do?ids=' + selectedList);
    };

    // 多条件分页查询规格数据
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../specification/search.do?page=' + page + "&size=" + size, searchEntity);
    };
});
