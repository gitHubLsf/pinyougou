pyg.service('loginService', function ($http) {

    // 获取当前登录的用户名
    this.getLoginName = function () {
        return $http.get('../login/getLoginName.do');
    }

});