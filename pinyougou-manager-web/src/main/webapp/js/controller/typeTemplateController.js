// 控制层 
pyg.controller('typeTemplateController', function ($scope, $controller, typeTemplateService, brandService, specificationService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope: $scope});


    // 查询所有数据  
    $scope.findAll = function () {
        typeTemplateService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };


    // 无条件分页查询
    $scope.findPage = function (page, size) {
        typeTemplateService.findPage(page, size).success(
            function (response) {
                // 修改当前页数据
                $scope.list = response.rows;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    // 根据 ID 查询模板数据
    $scope.findOne = function (id) {
        typeTemplateService.findOne(id).success(
            function (response) {
                // 将查询到的数据显示在修改框中
                $scope.templateEntity = response;
                // 注意：此处返回的 response 对象中的 brandIds 和 specIds 和 customAttributeItems 都是
                // 以字符串形式保存的集合，我们需要使用原生 JS 自带的转换器将字符串转换为 JSON 格式的对象
                $scope.templateEntity.brandIds = JSON.parse($scope.templateEntity.brandIds);
                $scope.templateEntity.specIds = JSON.parse($scope.templateEntity.specIds);
                $scope.templateEntity.customAttributeItems = JSON.parse($scope.templateEntity.customAttributeItems);
            }
        );
    };

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.templateEntity.id != null) {//如果有ID
            serviceObject = typeTemplateService.update($scope.templateEntity); //修改
        } else {
            serviceObject = typeTemplateService.add($scope.templateEntity);//增加
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
            typeTemplateService.batchDelete($scope.selectedList).success(
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
        typeTemplateService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.templateList = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };

    // 添加模板时，展示给用户选择的品牌列表数据
    // 1.静态模拟
    //  $scope.brandList = {
    //      data: [{id: 1, text: '联想'}, {id: 2, text: '中兴'}, {id: 3, text: '华为'}, {id: 4, text: '小米'}]
    //  };

    // 2.后端数据支撑
    $scope.brandList = {
        data: []
    };
    // 后端查询所有品牌下拉列表
    $scope.selectBrandList = function () {
        brandService.selectBrandList().success(
            function (response) {
                $scope.brandList = {data: response};
            }
        );
    };

    // 添加模板时，展示给用户选择的规格下拉列表数据
    $scope.specList = { data:[] };
    $scope.selectSpecList = function () {
        specificationService.selectSpecList().success(
            function (response) {
                $scope.specList = { data:response };
            }
        );
    };

    // 添加模板时，添加一行扩展属性
    $scope.addTableRow = function () {
        $scope.templateEntity.customAttributeItems.push({});
    };

    // 添加模板时，删除一行扩展属性
    $scope.deleteTableRow = function (index) {
        $scope.templateEntity.customAttributeItems.splice(index, 1);
    };



});	
