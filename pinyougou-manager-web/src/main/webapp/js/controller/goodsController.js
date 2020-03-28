// 控制层 
pyg.controller('goodsController', function ($scope,
                                            $controller,
                                            $location,
                                            goodsService,
                                            itemCatService,
                                            typeTemplateService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope: $scope});


    $scope.searchEntity = { auditStatus:'0' };//定义搜索对象

    //搜索
    $scope.search = function (page, size) {
        goodsService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.goodsList = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    $scope.goodsEntity = {tbGoods: {}, tbGoodsDesc: {itemImages: [], specificationItems: []}};


    // 添加商品时，查询所有一级分类列表
    $scope.selectCategory_1_List = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                // response 表示所有一级分类对象的集合
                $scope.category_1_List = response;
            }
        );
    };

    // 监控一级分类列表绑定的变量，如果变量发生改变，则开始查询二级分类列表
    $scope.$watch('goodsEntity.tbGoods.category1Id', function (newValue, oldValue) {
        // oldValue 代表之前的值，newValue 代表变化后的值
        // 开始查询二级列表
        itemCatService.findByParentId(newValue).success(
            function (response) {
                // response 表示所有二级分类对象的集合
                $scope.category_2_List = response;
            }
        );
    });


    // 监控二级分类列表绑定的变量，如果变量发生改变，则开始查询三级分类列表
    $scope.$watch('goodsEntity.tbGoods.category2Id', function (newValue, oldValue) {
        // oldValue 代表之前的值，newValue 代表变化后的值
        // 开始查询三级列表
        itemCatService.findByParentId(newValue).success(
            function (response) {
                // response 表示所有三级分类对象的集合
                $scope.category_3_List = response;
            }
        );
    });


    // 监控三级分类列表绑定的变量，如果变量发生改变，则展示三级分类对象对应的模板 ID
    $scope.$watch('goodsEntity.tbGoods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.goodsEntity.tbGoods.typeTemplateId = response.typeId;
            }
        );
    });


    // 监控模板 ID 变量，如果它的值发生改变，就要到后台查询该模板包含的所有品牌和扩展属性，和规格列表
    $scope.$watch('goodsEntity.tbGoods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {
                // 当前模板对应的品牌下拉列表
                $scope.selectBrandList = JSON.parse(response.brandIds);

                if ($location.search()['id'] == null) {
                    // 当前模板对应的扩展属性
                    $scope.goodsEntity.tbGoodsDesc.customAttributeItems
                        = JSON.parse(response.customAttributeItems);
                }
            }
        );

        // 商家添加商品时，查询规格列表
        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                // response 是个集合，集合里的元素是对象 { id:"", text:"", options:"" }
                $scope.specList = response;
            }
        );
    });


    // 显示商品状态
    // 0 未审核，1 已审核，2 审核未通过，3 关闭
    $scope.goodStatus = ['未审核', '已审核', '审核未通过', '关闭'];

    // 显示商品分类
    // 到后台查询商品分类列表，放在数组中，将分类的 ID 作为数组下标，分类名称作为值
    // 显示分类的时候，直接通过分类 ID  就能定位到分类名称

    // 商品分类数组
    $scope.itemCatArray =  [];
    $scope.findItemCatList = function () {
        itemCatService.findAll().success(
            function (response) {
                for (var i = 0; i < response.length; i++) {
                    $scope.itemCatArray[response[i].id] = response[i].name;
                }
            }
        );
    };

    // 修改商品信息时，根据商品 ID 查询商品信息
    $scope.findGoodById = function () {
        // 此处的 id 通过地址栏来获取，通过 $location 服务来获取参数 id
        // goods_edit.html#?id=xxxxx;
        var goodId = $location.search()['id'];
        // search() 方法查找的是页面地址栏中的所有参数，此处查找的是参数 id

        if (goodId == null) {
            // 由于添加商品和修改商品使用的是同一个页面 goods_edit.html
            // 所以此处要做个判断
            // 如果没有传递参数 id ，表示不是修改商品信息操作，而是增加商品操作
            return;
        }

        // 如果 id 不为空，说明是修改商品操作，就要到后台先查询商品信息
        goodsService.findOne(goodId).success(
            function (response) {
                // 此处的 goodsEntity 包含商品的所有信息（商品+商品详情+SKU列表）
                $scope.goodsEntity = response;

                // 提取商品详情中的商品介绍，填充在富文本编辑器中
                editor.html($scope.goodsEntity.tbGoodsDesc.introduction);

                // 将商品详情中的图片列表(string) 转成集合形式
                $scope.goodsEntity.tbGoodsDesc.itemImages
                    = JSON.parse($scope.goodsEntity.tbGoodsDesc.itemImages);

                // 将商品详情中的自定义扩展属性列表(string) 转成集合形式
                $scope.goodsEntity.tbGoodsDesc.customAttributeItems
                    = JSON.parse($scope.goodsEntity.tbGoodsDesc.customAttributeItems);

                // 将商品详情中的规格列表（string）转成集合形式
                $scope.goodsEntity.tbGoodsDesc.specificationItems
                    = JSON.parse($scope.goodsEntity.tbGoodsDesc.specificationItems);
                // 注意：上述转换的规格列表只是商家选择后的部分规格数据
                // 比如 [ { attributeName:'内存', attributeValue:[ '4G', '8G' ] ]
                // 而页面上此时展示的是模板对应的全部规格列表
                // 比如 [ { text:'内存', options:[ '4G', '8G', '16G', '32G' ] } ]
                // 此时我们在页面上展示规格列表，展示每个规格及规格选项时，需要判断此规格选项是否在商家选择后的规格列表中
                // 如果在才需要勾选，不再就不勾选
                // 此时要用到 ng-checked 指令 ng-checked="" 如果返回 true，则勾选，否则不勾选

                // 将商品 SKU 列表 tbItemList 中的 spec 属性（string）转换成对象形式
                for (var i = 0; i < $scope.goodsEntity.tbItemList.length; i++) {
                    $scope.goodsEntity.tbItemList[i].spec
                        = JSON.parse($scope.goodsEntity.tbItemList[i].spec);
                }
            }
        );
    };


    // 判断规格选项是否在商家选择后的规格列表中
    // specName 是规格名称，optionName 是规格选项名称
    $scope.checkSpecificationItem = function(specName, optionName) {
        // 获取商家选择后的规格列表
        var list = $scope.goodsEntity.tbGoodsDesc.specificationItems;
        // 判断商家自己的规格列表中是否有 specName 这个规格
        var obj = $scope.searchObjectKey(list, 'attributeName', specName);

        if (obj == null) {
            // 表示商家自己的规格列表没有这个规格，直接返回 false，代表不勾选
            return false;
        } else {
            // obj 表示对应 specName 的那个规格对象
            // 判断此对象的 attributeValue 集合中，是否有一个值叫做 optionName（规格选项名称）
            if (obj.attributeValue.indexOf(optionName) >= 0) {
                // 如果有，则返回 true
                return true;
            } else {
                // 没有，则返回 false
                return false;
            }
        }
    };

    // 运营商批量修改商品的审核状态
    $scope.updateGoodStatus = function (status) {
        goodsService.updateGoodStatus($scope.selectedList, status).success(
            function (response) {
                if (response.success) {
                    // 修改成功
                    alert(response.message);
                    // 刷新页面
                    $scope.search(1, $scope.paginationConf.itemsPerPage);
                } else {
                    alert(response.message);
                }
            }
        );

        // 清空保存需要审核商品 ID 的集合
        $scope.selectedList = [];
    };


    // 批量删除
    $scope.batchDelete = function () {
        if ($scope.selectedList.length === 0) {
            alert("请选择要删除的内容");
        } else {
            goodsService.batchDelete($scope.selectedList).success(
                function (response) {
                    if (response.success) {
                        // 删除成功
                        // 刷新列表
                        $scope.search(1, $scope.paginationConf.itemsPerPage);
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
