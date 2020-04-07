pyg.controller('itemSearchController', function ($scope,
                                              itemSearchService) {

    // 商品搜索
    $scope.itemSearch = function (searchMap) {
        itemSearchService.itemSearch(searchMap).success(
            function (response) {
                // 返回的 response 是 Map 集合, key 为 rows 的键保存查询结果
                $scope.resultMap = response;
            }
        );
    };

});