// 上传功能相关的服务层
pyg.service('uploadService', function ($http) {

    // 上传图片
    this.uploadImg = function () {

        var formData = new FormData();

        // file 是页面中的文件上传框，一个页面可能有多个文件上传框, 这些文件上传框如果 name 属性值都为 file
        // 那么此处的 file.files 数组就能获取到这些文件上传框

        // 我们在商品编辑页面，只设置一个文件上传框，id 为 file，所以此处 file.files[0] 获取的就是唯一的一个文件上传框对象
        formData.append("file", file.files[0]);

        return $http({
            method: 'POST',
            url: "../upload.do",
            data: formData,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        });

    };
});