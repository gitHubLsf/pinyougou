// 用户登录服务层
pyg.service('loginService', function ($http) {

    // 获取登录的用户名
    this.getLoginUserName = function () {
        return $http.get('login/name.do');
    }
});