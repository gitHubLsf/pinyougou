pyg.service('contentService', function ($http) {

    // 查询指定广告分类 ID 下的所有广告
    this.findByContentCategoryId = function (categoryId) {
        return $http.get('content/findByContentCategoryId.do?categoryId=' + categoryId);
    };

});