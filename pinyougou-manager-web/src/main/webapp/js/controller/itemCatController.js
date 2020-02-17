// 商品分类控制层
pyg.controller('itemCatController', function ($scope, $controller, itemCatService, typeTemplateService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope: $scope});


    // 查询所有数据  
    $scope.findAll = function () {
        itemCatService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };


    // 无条件分页查询
    $scope.findPage = function (page, size) {
        itemCatService.findPage(page, size).success(
            function (response) {
                // 修改当前页数据
                $scope.list = response.rows;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    // 根据 ID 查询某种商品分类
    $scope.findOne = function (id) {
        itemCatService.findOne(id).success(
            function (response) {
                // 将查询到的数据显示在修改框中
                $scope.itemCatEntity = response;
                $scope.itemCatEntity.types = { id:response.typeId, text:response.typeName };
            }
        );
    };

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.itemCatEntity.id != null) {//如果有ID
            // 修改商品信息
            $scope.itemCatEntity.typeId = $scope.itemCatEntity.types.id;
            serviceObject = itemCatService.update($scope.itemCatEntity);
        } else {
            // 增加商品分类
            $scope.itemCatEntity.parentId = $scope.parentId;
            $scope.itemCatEntity.typeId = $scope.itemCatEntity.types.id;
            serviceObject = itemCatService.add($scope.itemCatEntity);
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    // 添加成功，刷新列表
                    $scope.findByParentId($scope.parentId);
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
            itemCatService.batchDelete($scope.selectedList).success(
                function (response) {
                    if (response.success) {
                        // 删除成功
                        // 刷新
                        $scope.findByParentId($scope.parentId);
                    } else {
                        // 删除失败
                        alert(response.message);
                        // 刷新
                        $scope.findByParentId($scope.parentId);
                    }
                });

            // 删除成功后，要清空集合
            $scope.selectedList = [];
        }
    };


    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, size) {
        itemCatService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.list = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    // 根据上级 ID 查询商品分类
    $scope.findByParentId = function (parentId) {
        $scope.parentId = parentId;
        itemCatService.findByParentId(parentId).success(
            function (response) {
                $scope.itemCatList = response;
            }
        );
    };


    // 初始时的级别为 1
    $scope.grade = 1;

    // 更新当前的级别
    $scope.updateGrade = function (value) {
        $scope.grade = value;
    };

    //更新商品分类列表
    $scope.selectItemCatList = function (entity) {
        if ($scope.grade === 1) {
            $scope.entity_1 = null;
            $scope.entity_2 = null;
        } else if ($scope.grade === 2) {
            $scope.entity_1 = entity;
            $scope.entity_2 = null;
        } else if ($scope.grade === 3) {
            $scope.entity_2 = entity;
        }
        $scope.findByParentId(entity.id);
    };


    // 新建商品分类时，保存上级 ID 的值
    // 这个值需要经常变化，初始为 0,
    // 点击面包屑的顶级列表时，该值重置为 0
    // 点击 entity_1 时，该值重置为 entity_1.id
    // 点击 entity_2 时，该值重置为 entity_2.id
    // 点击查看下级按钮时，该值重置为 entity.id
    $scope.parentId = 0;

    // 保存类型模板对象
    $scope.typeList = {
        data: []
    };
    // 查询类型模板列表
    $scope.findTypeList = function () {
        typeTemplateService.findTypeList().success(
            function (response) {
                $scope.typeList = {data: response};
            }
        );
    };
});	
