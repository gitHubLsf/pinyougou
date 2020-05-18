// 用户服务层
pyg.service('userService', function ($http) {

    // 查询所有数据
    this.findAll = function () {
        return $http.get('user/findAll.do');
    };

    // 无条件分页查询
    this.findPage = function (page, size) {
        return $http.get('user/findPage.do?page=' + page + '&size=' + size);
    };

    // 根据 ID 查询实体
    this.findOne = function (id) {
        return $http.get('user/findOne.do?id=' + id);
    };

    // 用户注册
    this.add = function (entity, smsCode) {
        return $http.post('user/add.do?smsCode=' + smsCode, entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.post('user/update.do', entity);
    };

    // 批量删除
    this.batchDelete = function (selectedList) {
        return $http.get('user/batchDelete.do?ids=' + selectedList);
    };

    // 多条件分页查询
    this.findPageLimit = function (page, size, searchEntity) {
        return $http.post('user/search.do?page=' + page + "&size=" + size, searchEntity);
    };

    // 发送短信验证码
    this.sendSmsCode = function (phone) {
        return $http.get("user/sendSmsCode.do?phone=" + phone);
    };
});
