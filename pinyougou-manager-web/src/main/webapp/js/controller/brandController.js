// 品牌列表管理控制器 brandController,该控制器依赖品牌列表服务模块
pyg.controller('brandController', function ($scope, $controller, brandService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope:$scope});


    // 在控制器中添加查询所有品牌列表的方法
    $scope.findAll = function () {
        // 在控制器中调用品牌列表服务模块的功能
        brandService.findAll().success(
            function (response) {
                // response 就是后台返回的 json 格式存储的品牌列表数据
                $scope.brandList = response;
            }
        );
    };


    // 添加品牌分页查询的方法
    $scope.findPage = function (page, size) {
        brandService.findPage(page, size).success(
            function (response) {
                // 修改当前页数据
                $scope.brandList = response.rows;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };

    // 定义搜索对象
    $scope.searchEntity = {};

    // 多条件品牌分页查询
    $scope.search = function (page, size) {
        brandService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.brandList = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };

    // 保存品牌(可能是新增品牌，可能是修改品牌)
    $scope.save = function () {
        var object = null;
        if ($scope.brandEntity.id != null) {
            // id 不为空，说明是修改品牌
            object = brandService.update($scope.brandEntity);
        } else {
            object = brandService.add($scope.brandEntity);
        }

        object.success(
            function (response) {
                if (response.success) {
                    // 保存成功
                    // 刷新当前页
                    $scope.reloadList();
                } else {
                    // 保存失败, 提示错误信息
                    alert(response.message);
                }
            }
        );
    };

    // 根据品牌 ID 查询品牌信息
    $scope.findOne = function (id) {
        brandService.findOne(id).success(
            function (response) {
                // 将查询到的数据显示在修改框中
                $scope.brandEntity = response;
            }
        );
    };


    // 监听删除按钮
    $scope.batchDelete = function () {
        if ($scope.selectedList.length === 0) {
            alert("请选择要删除的内容");
        } else {
            brandService.batchDelete($scope.selectedList).success(
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
});