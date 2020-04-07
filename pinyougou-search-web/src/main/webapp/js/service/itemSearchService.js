pyg.service('itemSearchService', function ($http) {

    // 商品搜索
    this.itemSearch = function (searchMap) {
        return $http.post('itemSearch/search.do', searchMap);
    };

});