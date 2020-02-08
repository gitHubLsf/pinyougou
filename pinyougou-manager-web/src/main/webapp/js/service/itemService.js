// 服务层
pyg.service('itemService', function ($http) {

    // 查询所有数据
    this.findAll = function () {
        return $http.get('../item/findAll.do');
    };

    // 无条件分页查询
    this.findPage = function (page, size) {
        return $http.get('../item/findPage.do?page=' + page + '&size=' + size);
    };

    // 根据 ID 查询实体
    this.findOne = function (id) {
        return $http.get('../item/findOne.do?id=' + id);
    };

    // 增加
    this.add = function (entity) {
        return $http.post('../item/add.do', entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.post('../item/update.do', entity);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('../item/batcheDelete.do?ids=' + selectedList);
    };

    // 多条件分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../item/search.do?page=' + page + "&size=" + size, searchEntity);
    };
});
