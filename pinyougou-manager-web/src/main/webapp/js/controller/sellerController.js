// 控制层 
pyg.controller('sellerController', function ($scope, $controller, sellerService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope: $scope});


    // 查询所有数据  
    $scope.findAll = function () {
        sellerService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };


    // 无条件分页查询
    $scope.findPage = function (page, size) {
        sellerService.findPage(page, size).success(
            function (response) {
                // 修改当前页数据
                $scope.list = response.rows;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    // 根据 ID 查询某个未审核商家的全部信息
    $scope.findOne = function (id) {
        sellerService.findOne(id).success(
            function (response) {
                // 将查询到的数据显示在修改框中
                $scope.sellerEntity = response;
            }
        );
    };

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = sellerService.update($scope.entity); //修改
        } else {
            serviceObject = sellerService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    };


    // 商家入驻申请
    $scope.add = function () {
        sellerService.add($scope.sellerEntity).success(
            function (response) {
                if (response.success) {
                    alert(response.message);

                   // 入驻申请提交成功，跳转到登录页面
                    location.href = 'shoplogin.html';
                } else {
                    alert(response.message);
                }
            }
        );
    };


    // 修改商家的审核状态
    $scope.update = function (status) {
        $scope.sellerEntity.status = status;
        sellerService.update($scope.sellerEntity).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.search(1, $scope.paginationConf.itemsPerPage);
                } else {
                    alert(response.message);
                }
            }
        );
    };


    // 批量删除
    $scope.batchDelete = function () {
        if ($scope.selectedList.length === 0) {
            alert("请选择要删除的内容");
        } else {
            sellerService.batchDelete($scope.selectedList).success(
                function (response) {
                    if (response.success) {
                        // 删除成功
                        // 刷新当前页
                        $scope.reloadList();
                    } else {
                        // 删除失败
                        alert(response.message);
                    }
                });

            // 删除成功后，要清空集合
            $scope.selectedList = [];
        }
    };


    $scope.searchEntity = {};//定义搜索对象

    // 多条件分页查询未审核商家列表
    $scope.search = function (page, size) {
        sellerService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.sellerList = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };

});	
