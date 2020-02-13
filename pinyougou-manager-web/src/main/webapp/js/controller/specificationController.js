// 规格控制层
pyg.controller('specificationController', function ($scope, $controller, specificationService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope: $scope});


    // 查询所有规格数据
    $scope.findAll = function () {
        specificationService.findAll().success(
            function (response) {
                $scope.specList = response;
            }
        );
    };


    // 无条件分页查询所有规格数据
    $scope.findPage = function (page, size) {
        specificationService.findPage(page, size).success(
            function (response) {
                // 修改当前页数据
                $scope.specList = response.rows;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    // 根据 ID 查询某种规格的数据
    $scope.findOne = function (id) {
        specificationService.findOne(id).success(
            function (response) {
                // 将查询到的数据显示在修改框中
                $scope.specEntity = response;
            }
        );
    };

    // 保存规格数据（规格+规格选项）
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.specEntity.specification.id != null) {//如果有ID
            serviceObject = specificationService.update($scope.specEntity); //修改
        } else {
            serviceObject = specificationService.add($scope.specEntity);//增加
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


    // 批量删除
    $scope.batchDelete = function () {
        if ($scope.selectedList.length === 0) {
            alert("请选择要删除的内容");
        } else {
            specificationService.batchDelete($scope.selectedList).success(
                function (response) {
                    if (response.success) {
                        // 删除成功
                        // 刷新到第一页
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

    // 定义搜索对象
    $scope.searchEntity = {};

    // 多条件分页查询规格数据
    $scope.search = function (page, size) {
        specificationService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.specList = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };

    // { specification:{}, specificationOptionList:[ {}, {}, ... ] }
    // 创建 specEntity 对象，该对象是提交给后端的，包含 specification 属性：对象，specificationOptionList 属性：数组
    // 注意，该对象在每次点击新建按钮时，都要清空，所以将创建位置移动到新建按钮的 ng-click 事件
    // $scope.specEntity = { specification:{}, specificationOptionList:[] };

    // 添加规格选项时，在表格中增加一行
    $scope.addTableRow = function () {
        $scope.specEntity.specificationOptionList.push({});
    };

    // 删除规格选项表格中的某一行
    $scope.deleteTableRow = function (index) {
        // index 是 ng-repeat 循环的每一个对象的索引，在这里就是某一行的索引
        $scope.specEntity.specificationOptionList.splice(index, 1);
    };



});	
