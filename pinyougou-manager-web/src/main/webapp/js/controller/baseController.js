// 基础控制器，所有控制器的父类
pyg.controller('baseController', function ($scope) {

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
});




