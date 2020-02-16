pyg.controller('indexController', function ($scope, loginService) {

    // 获取当前登录的用户名
    $scope.getLoginName = function () {
        return loginService.getLoginName().success(
            function (response) {
                $scope.loginName = response.loginName;
            }
        );
    };

});