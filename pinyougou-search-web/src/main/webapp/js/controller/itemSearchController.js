pyg.controller('itemSearchController', function ($scope,
                                              itemSearchService) {

    // 构建前端传递给后端的搜索条件
    $scope.searchMap = {
        keywords:'',    // 搜索关键字
        category:'',    // 商品分类名称
        brand:'',       // 品牌名称
        spec:{}         // 规格对象
    };

    // 根据用户选择的搜索条件，动态创建搜索条件
    $scope.addSearchItem = function(key, value) {
        if (key == 'category' || key == 'brand') {
            // 设置搜索条件中的商品分类名称或者品牌名称
            $scope.searchMap[key] = value;
        } else {
            // 设置搜索条件中的规格对象
            $scope.searchMap.spec[key] = value;
        }

        // 到后端进行过滤查询
        $scope.itemSearch(1);
    };

    // 根据用户的操作，撤销某些搜索条件
    $scope.removeSearchItem = function(key) {
        if (key == 'category' || key == 'brand') {
            // 撤销搜索条件中的商品分类名称或者品牌名称
            $scope.searchMap[key] = '';
        } else {
            // 撤销搜索条件中的规格对象中的某对 k-v
            delete $scope.searchMap.spec[key];
        }

        // 到后端进行过滤查询
        $scope.itemSearch(1);
    };

    // 商品搜索
    $scope.itemSearch = function (id) {
        // id = 1: 表示增加或者撤销搜索条件时，调用该方法，此时对搜索条件不做修改
        if (id == 0) {
            // 表示在 search.html 页面，点击搜索按钮，此时搜索条件 searchMap 除 keywords 之外的其他条件要清空
            $scope.searchMap.category = "";
            $scope.searchMap.brand = "";
            $scope.searchMap.spec = {};
        }

        itemSearchService.itemSearch($scope.searchMap).success(
            function (response) {
                // 返回的 response 是 Map 集合, key 为 rows 的键保存查询结果
                $scope.resultMap = response;
            }
        );
    };

});