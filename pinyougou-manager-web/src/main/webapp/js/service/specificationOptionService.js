// 服务层
pyg.service('specificationOptionService', function ($http) {

    // 查询所有数据
    this.findAll = function () {
        return $http.get('../specificationOption/findAll.do');
    };

    // 无条件分页查询
    this.findPage = function (page, size) {
        return $http.get('../specificationOption/findPage.do?page=' + page + '&size=' + size);
    };

    // 根据 ID 查询实体
    this.findOne = function (id) {
        return $http.get('../specificationOption/findOne.do?id=' + id);
    };

    // 增加
    this.add = function (entity) {
        return $http.post('../specificationOption/add.do', entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.post('../specificationOption/update.do', entity);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('../specificationOption/batcheDelete.do?ids=' + selectedList);
    };

    // 多条件分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../specificationOption/search.do?page=' + page + "&size=" + size, searchEntity);
    };
});
