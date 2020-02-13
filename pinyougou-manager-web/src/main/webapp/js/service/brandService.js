// 品牌列表服务模块(自定义服务，依赖 $http 内置服务)
pyg.service('brandService', function ($http) {
    // 查询所有品牌
    this.findAll = function () {
        return $http.get('../brand/findAll.do');
    };

    // 无条件品牌分页查询
    this.findPage = function (page, size) {
        return $http.get('../brand/findPage.do?page=' + page + '&size=' + size);
    };

    // 多条件品牌分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../brand/search.do?page=' + page + '&size=' + size, searchEntity);
    };

    // 添加品牌
    this.add = function (brandEntity) {
        return $http.post('../brand/add.do', brandEntity);
    };

    // 修改品牌
    this.update = function (brandEntity) {
        return $http.post('../brand/update.do', brandEntity);
    };

    // 根据品牌 ID 查询品牌信息
    this.findOne = function (id) {
        return $http.get('../brand/findOne.do?id=' + id);
    };

    // 批量删除品牌
    this.batchDelete = function (selectedList) {
        return $http.get('../brand/batchDelete.do?ids=' + selectedList);
    };

    // 查询所有品牌的下拉列表
    this.selectBrandList = function () {
        return $http.get('../brand/selectBrandList.do');
    }
});