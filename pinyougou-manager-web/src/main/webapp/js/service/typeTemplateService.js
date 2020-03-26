// 服务层
pyg.service('typeTemplateService', function ($http) {

    // 查询所有数据
    this.findAll = function () {
        return $http.get('../typeTemplate/findAll.do');
    };

    // 无条件分页查询
    this.findPage = function (page, size) {
        return $http.get('../typeTemplate/findPage.do?page=' + page + '&size=' + size);
    };

    // 根据 ID 查询实体
    this.findOne = function (id) {
        return $http.get('../typeTemplate/findOne.do?id=' + id);
    };

    // 增加
    this.add = function (entity) {
        return $http.post('../typeTemplate/add.do', entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.post('../typeTemplate/update.do', entity);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('../typeTemplate/batchDelete.do?ids=' + selectedList);
    };

    // 多条件分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('../typeTemplate/search.do?page=' + page + "&size=" + size, searchEntity);
    };

    // 添加商品分类时，查询所有类型模板列表
    this.findTypeList = function () {
        return $http.get('../typeTemplate/findTypeList.do');
    };

    // 商家添加商品时，输入规格列表时，查询商品所属的模板的规格列表
    this.findSpecList = function (id) {
        return $http.get('../typeTemplate/findSpecList.do?id=' + id);
    }
});
