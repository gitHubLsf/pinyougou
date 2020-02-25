// 控制层 
pyg.controller('goodsController', function ($scope,
                                            $controller,
                                            goodsService,
                                            uploadService,
                                            itemCatService,
                                            typeTemplateService) {

    // 继承父类控制器 baseController
    $controller('baseController', {$scope: $scope});


    // 查询所有数据  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };


    // 无条件分页查询
    $scope.findPage = function (page, size) {
        goodsService.findPage(page, size).success(
            function (response) {
                // 修改当前页数据
                $scope.list = response.rows;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    // 根据 ID 查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                // 将查询到的数据显示在修改框中
                $scope.entity = response;
            }
        );
    };

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    };


    // 添加商品
    $scope.add = function () {
        // 获取富文本编辑器 editor 中，用户输入的商品介绍的内容
        $scope.goodsEntity.tbGoodsDesc.introduction = editor.html();

        goodsService.add($scope.goodsEntity).success(
            function (response) {
                if (response.success) {
                    // 添加商品成功
                    alert(response.message);
                    // 清空输入框，让商家继续录入商品
                    $scope.goodsEntity = {};
                    // 清空富文本编辑器中的商品介绍内容
                    editor.html('');
                } else {
                    alert(response.message);
                }
            }
        );
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
                        // 刷新当前页
                        $scope.reloadList();
                    } else {
                        // 删除失败
                        alert(response.message);
                    }
                });

            // 删除成功后，要清空集合
            $scope.selectedList = [];
        }
    };


    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, size) {
        goodsService.findPageLimit(page, size, $scope.searchEntity).success(
            function (response) {
                // 修改当前页数据
                $scope.list = response.rows;
                // 修改页码
                $scope.paginationConf.currentPage = page;
                // 修改总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };


    // 上传商品图片
    $scope.uploadImg = function () {
        uploadService.uploadImg()
            .success(
                function (response) {
                    if (response.success) {
                        // 上传成功后，修改页面中 img 标签的 src 属性
                        $scope.imgEntity.url = response.message;
                    } else {
                        alert(response.message);
                    }
                })
            .error(
                function () {
                    alert("上传发生错误");
                }
            );
    };

    $scope.goodsEntity = {tbGoods: {}, tbGoodsDesc: {itemImages: []}};

    // 将图片实体添加到 goodsEntity.tbGoodsDesc.itemImages 集合中
    $scope.addImgEntity = function () {
        $scope.goodsEntity.tbGoodsDesc.itemImages.push($scope.imgEntity);
    };

    // 删除图片集合中的某张图片
    $scope.deleteImgEntity = function (index) {
        $scope.goodsEntity.tbGoodsDesc.itemImages.splice(index, 1);
    };

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


    // 监控模板 ID 变量，如果它的值发生改变，就要到后台查询该模板包含的所有品牌和扩展属性
    $scope.$watch('goodsEntity.tbGoods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {

                // 当前模板对应的品牌下拉列表
                $scope.selectBrandList = JSON.parse(response.brandIds);

                // 当前模板对应的扩展属性
                $scope.goodsEntity.tbGoodsDesc.customAttributeItems = JSON.parse(response.customAttributeItems);
            }
        );
    });

});	
