// 服务层
pyg.service('itemCatService', function ($http) {

    // 查询所有数据
    this.findAll = function () {
        return $http.get('../itemCat/findAll.do');
    };

    // 无条件分页查询
    this.findPage = function (page, size) {
        return $http.get('../itemCat/findPage.do?page=' + page + '&size=' + size);
    };

    // 根据 ID 查询实体
    this.findOne = function (id) {
        return $http.get('../itemCat/findOne.do?id=' + id);
    };

    // 增加
    this.add = function (entity) {
        return $http.post('../itemCat/add.do', entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.post('../itemCat/update.do', entity);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('../itemCat/batcheDelete.do?ids=' + selectedList);
    };

    // 多条件分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../itemCat/search.do?page=' + page + "&size=" + size, searchEntity);
    };
});
