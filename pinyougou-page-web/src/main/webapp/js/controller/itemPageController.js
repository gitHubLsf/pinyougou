pyg.controller('itemPageController', function ($scope) {

    // 记录购买数量,默认为 1
    $scope.num = 1;


    // 购买数量的增减
    $scope.changeNum = function (n) {
        $scope.num += n;
        if ($scope.num < 1)
            $scope.num = 1;	// 如果一直减少购买数量到小于 1，就重新置为默认值 1
    };


    // 记录用户选择的规格及规格选项
    // 例如：{ '网络制式':'4G', '内存':'16G' }
    $scope.specItems = {};


    // 用户选择某种规格及规格选项时
    $scope.selectSpec = function (key, value) {
        $scope.specItems[key] = value;
        // 例如 $scope.specificationItems['网络制式'] = '4G';

        // 根据规格及规格选项查找 SKU
        searchSKU();
    };


    // 记录用户选择的 SKU
    $scope.sku = {};
    $scope.loadDefaultSKU = function () {
        if (skuList.length > 0) {
            $scope.sku = skuList[0];	// 初始时，默认选择的 SKU 是 SKU 列表中的第一条默认 SKU 记录
            // 将 SKU 对象的 sec 属性（对象形式）深拷贝给用户选择的规格及规格选项
            $scope.specItems = JSON.parse(JSON.stringify($scope.sku.spec));
        }
    };


    // 根据用户选择的规格及规格选项，在 SKU 列表中查找 SKU
    searchSKU = function () {
        for (var i in skuList) {
            // 判断 skuList[i].spec 对象是否和 $scope.specItems 对象相等
            if (matchObj(skuList[i].spec, $scope.specItems)) {
                // 如果相等，就将 skuList[i] 对象赋值给 $scope.sku 对象，表示用户选择了新的 SKU
                $scope.sku = skuList[i];
                return;
            }
        }

        // 如果找不到 SKU，就显示错误提示
        $scope.sku = {"id": -1, "title": "---", "price": 0.0};
    };


    // 判断两个对象是否相等（也就是对象之间的键和值要相等）
    matchObj = function (obj1, obj2) {
        for (var i in obj1) {
            if (obj1[i] != obj2[i])
                return false;
        }

        for (var i in obj2) {
            if (obj2[i] != obj1[i])
                return false;
        }

        return true;
    };


    // 添加商品到购物车
    $scope.addToCar = function () {
        alert("SKU_ID:" + $scope.sku.id);
    }

});