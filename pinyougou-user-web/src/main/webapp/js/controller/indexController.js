// 用户中心主页控制器
pyg.controller('indexController', function ($scope, loginService) {

    // 获取当前登录的用户名
    $scope.getLoginUserName = function () {
        loginService.getLoginUserName().success(
            function (response) {
                $scope.loginUserName = response.loginUserName;
            }
        );
    }
});