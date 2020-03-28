// 广告管理控制层
pyg.controller('contentController', function ($scope,
											  $controller,
											  contentService,
                                              uploadService,
                                              contentCategoryService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope: $scope});


    // 查询所有数据  
    $scope.findAll = function () {
        contentService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };


    // 根据 ID 查询实体
    $scope.findOne = function (id) {
        contentService.findOne(id).success(
            function (response) {
                $scope.contentEntity = response;
            }
        );
    };


    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.contentEntity.id != null) {//如果有ID
            serviceObject = contentService.update($scope.contentEntity); //修改
    } else {
            serviceObject = contentService.add($scope.contentEntity);//增加
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
            contentService.batchDelete($scope.selectedList).success(
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
        contentService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.contentList = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    // 上传商品图片
    $scope.uploadImg = function () {
        uploadService.uploadImg()
            .success(
                function (response) {
                    if (response.success) {
                        // 上传成功后，修改页面中 img 标签的 src 属性
                        $scope.contentEntity.pic = response.message;
                    } else {
                        alert(response.message);
                    }
                })
            .error(
                function () {
                    alert("上传发生错误");
                }
            );
    };


    // 查询所有广告分类下拉列表
    $scope.selectContentCategoryList = function () {
        contentCategoryService.findAll().success(
            function (response) {
                $scope.contentCategoryList = response;
            }
        );
    };

    // 广告状态，0 无效，1 有效
    $scope.contentStatus = [ '无效', '有效' ];
});	
