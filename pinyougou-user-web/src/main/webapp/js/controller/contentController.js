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

    // 接收主页的搜索关键字，然后通过路由携带搜索关键字
    // 跳转到 pinyougou-search-web 模块的 search.html 页面
    $scope.search = function () {
        // 去除搜索关键字 keywords 的所有空格
        var keywords = $scope.keywords.replace(/\s/ig,'');
        if (keywords == null || keywords == "") {
            return;
        }

        location.href = 'http://localhost:9104/search.html#?keywords=' + keywords;
    };

});