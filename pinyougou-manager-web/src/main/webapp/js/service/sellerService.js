// 商家管理服务层
pyg.service('sellerService', function ($http) {

    // 查询所有数据
    this.findAll = function () {
        return $http.get('../seller/findAll.do');
    };

    // 无条件分页查询
    this.findPage = function (page, size) {
        return $http.get('../seller/findPage.do?page=' + page + '&size=' + size);
    };

    // 根据 ID 查询某个未审核商家的全部信息
    this.findOne = function (id) {
        return $http.get('../seller/findOne.do?id=' + id);
    };

    // 商家入驻申请
    this.add = function (entity) {
        return $http.post('seller/add.do', entity);
    };

    // 修改商家的审核状态
    this.update = function (entity) {
        return $http.post('../seller/update.do', entity);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('../seller/batchDelete.do?ids=' + selectedList);
    };

    // 多条件分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../seller/search.do?page=' + page + "&size=" + size, searchEntity);
    };
});
