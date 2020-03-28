pyg.controller('contentController', function ($scope,
                                              contentService) {

    // 存储各分类广告的集合，每一个元素是一个集合，存储某个分类包含的广告
    $scope.contentList = [];

    // 查询指定广告分类 ID 下的所有广告
    $scope.findByContentCategoryId = function (categoryId) {
        contentService.findByContentCategoryId(categoryId).success(
            function (response) {
                // 此处查找的是首页轮播的广告，也就是 categoryId = 1，我们将它保存在集合 contentList 的下标为 1 的位置
                $scope.contentList[categoryId] = response;
            }
        );
    };

});