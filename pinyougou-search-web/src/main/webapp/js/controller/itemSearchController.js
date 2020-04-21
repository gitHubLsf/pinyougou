pyg.controller('itemSearchController', function ($scope,
                                              itemSearchService) {

    // 构建前端传递给后端的搜索条件
    $scope.searchMap = {
        keywords:'',    // 搜索关键字
        category:'',    // 商品分类名称
        brand:'',       // 品牌名称
        spec:{},        // 规格对象
        price:'',       // 价格区间（字符串形式：例如：0-500）
        pageNo:1,     // 页码
        pageSize:1,   // 每页记录数
        sort:'',        // 排序方式，例如 ASC 表示升序，DESC 表示降序
        sortField:''    // 排序关键字，例如 price 表示价格
    };

    // 根据用户选择的搜索条件，动态创建搜索条件
    $scope.addSearchItem = function(key, value) {
        if (key == 'category' || key == 'brand' || key == 'price') {
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
        if (key == 'category' || key == 'brand' || key == 'price') {
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

        if (id == 0) {
            // 表示在 search.html 页面，点击搜索按钮
            // 此时搜索条件 searchMap 除 keywords 之外的其他条件要清空
            // 页码 pageNo 置为 1
            $scope.searchMap.category = "";
            $scope.searchMap.brand = "";
            $scope.searchMap.spec = {};
            $scope.searchMap.price = "";
            $scope.searchMap.pageNo = 1;
        } else if (id == 1) {
            // id = 1: 表示增加或者撤销搜索条件时或者按关键字排序时，调用该方法
            // 此时对搜索条件不做修改
            // 页码 pageNo 置为 1
            $scope.searchMap.pageNo = 1;
        }

        // id = 2 表示切换页码时发起的搜索

        // 去除搜索条件中关键字 keywords 的所有空格
        var keywords = $scope.searchMap.keywords.replace(/\s/ig,'');
        if (keywords == null || keywords == "") {
            return;
        }
        $scope.searchMap.keywords = keywords;

        itemSearchService.itemSearch($scope.searchMap).success(
            function (response) {
                // 返回的 response 是 Map 集合, key 为 rows 的键保存查询结果
                $scope.resultMap = response;

                // 配置分页信息
                setPageInfo();
            }
        );
    };

    // 配置分页信息
    setPageInfo = function () {

        // 页码集合
        $scope.pageArray = [];
        // 显示页码时，我们只显示以当前页 pageNo 为中心的 5 页即可
        // 例如，pageNo 为 10 时，显示 8 9 10 11 12
        // 如果总页数 <= 5 页，就显示所有的页码
        // 如果总页数 > 5 页
        // 如果当前页 pageNo <= 3，就显示前 5 页
        // 如果当前页 pageNo >= 总页数-2，例如总页数为 100，当前页为 99 时，就显示 96 97 98 99 100，也就是显示后 5 页
        // 其他情况下，就显示 pageNo - 2, pageNo - 1, pageNo, pageNo + 1, pageNo + 2

        var currentPage = $scope.searchMap.pageNo;// 当前页码
        var totalPage = $scope.resultMap.totalPage; // 总页码
        var firstPage = 1;  // 开始页码
        var lastPage = totalPage;  // 截至页码

        $scope.showFirstDot = false;    // 前面无点
        $scope.showLastDot = false;     // 后面无点

        if (totalPage > 5) {
            if (currentPage <= 3) {
                lastPage = 5;
                $scope.showLastDot = true;   // 后面有点
            } else if (currentPage >= (totalPage - 2)) {
                firstPage = totalPage - 4;
                $scope.showFirstDot = true;    // 前面有点
            } else {
                firstPage = currentPage - 2;
                lastPage = currentPage + 2;
                // 前后都有点
                $scope.showFirstDot = true;
                $scope.showLastDot = true;
            }
        }

        // 往页码集合中添加页码
        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageArray.push(i);
        }

    };

    // 分页查询（包含点击页码时的分页，以及点击上一页和下一页时的分页）
    $scope.queryPage = function (page) {

        // 判断 page 是否为一个数
        if (isNaN(page)) {
            // 不是一个数
            return;
        }

        // 将 page 转换为数值类型
        page = Number(page);

        // 防止点击上一页或者下一页时，出现 page 越界
        if (page < 1 || page > $scope.resultMap.totalPage) {
            return;
        }

        // 修改当前页码
        $scope.searchMap.pageNo = page;

        // 调用 itemSearch 方法
        // itemSearch(2) 表示切换页码时发起的搜索
        $scope.itemSearch(2);
    };


    // 按关键字排序查询
    $scope.sortSearch = function (sort, sortField) {
        $scope.searchMap.sort = sort;
        $scope.searchMap.sortField = sortField;

        // 调用 itemSearch 方法
        $scope.itemSearch(1);
    }
});