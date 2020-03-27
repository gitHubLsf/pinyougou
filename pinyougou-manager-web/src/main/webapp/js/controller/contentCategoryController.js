// 广告分类管理控制层
pyg.controller('contentCategoryController', function ($scope,
													  $controller,
													  contentCategoryService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope: $scope});


    // 查询所有数据  
    $scope.findAll = function () {
        contentCategoryService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };


    // 根据 ID 查询实体
    $scope.findOne = function (id) {
        contentCategoryService.findOne(id).success(
            function (response) {
                // 将查询到的数据显示在修改框中
                $scope.contentCategoryEntity = response;
            }
        );
    };


    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.contentCategoryEntity.id != null) {//如果有ID
            serviceObject = contentCategoryService.update($scope.contentCategoryEntity); //修改
        } else {
            serviceObject = contentCategoryService.add($scope.contentCategoryEntity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
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
            contentCategoryService.batchDelete($scope.selectedList).success(
                function (response) {
                    if (response.success) {
                        // 删除成功
                        // 刷新当前页
                        $scope.search(1, $scope.paginationConf.itemsPerPage);
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

    //搜索
    $scope.search = function (page, size) {
        contentCategoryService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.contentCategoryList = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };

});	
