// 品牌列表管理控制器 brandController,该控制器依赖品牌列表服务模块
pyg.controller('brandController', function ($scope, brandService) {

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

    // 分页控件配置
    // currentPage：当前页,初始为第 1 页,当用户切换页面时,这个值自动改变
    // totalItems：总记录数
    // itemsPerPage：每页显示的记录数,前端可随时修改
    // perPageOptions：提供给用户选择的每页记录数窗口
    // onChange：当用户切换页面时,自动调用 onChange 指定的方法
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 5,
        perPageOptions: [5, 10, 15, 20, 25],
        onChange: function () {
            $scope.reloadList(); // 刷新品牌列表
        }
    };

    // 刷新品牌列表
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
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

    // 批量删除品牌
    // 创建集合，保存用户勾选的要删除的品牌的 ID
    $scope.selectedList = [];
    // 监听用户勾选
    $scope.updateSelected = function ($event, id) {
        if ($event.target.checked) {
            // 用户将复选框从无勾成有，表示要删除
            $scope.selectedList.push(id);
        } else {
            // 用户将复选框从有勾成无，表示不想删除了
            // 查找到这个元素，将其下标保存在 index 中
            var index = $scope.selectedList.indexOf(id);
            // 在集合中找到下标为 index 的位置,从这个位置开始,删除 1 个元素
            $scope.selectedList.splice(index, 1);
        }
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