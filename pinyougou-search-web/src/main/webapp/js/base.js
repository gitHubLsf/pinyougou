// 基础模块 pinyougou
var pyg = angular.module('pinyougou', []);

// 添加过滤器
// 过滤器名称 trustHtml
// 过滤器依赖 $sce 服务
// data 表示原始的，不受信任的 html 代码
// trustAsHtml(data) 表示对 data 内容进行信任，并将信任的结果返回
pyg.filter('trustHtml', ['$sce', function ($sce) {
    return function (data) {
        return $sce.trustAsHtml(data);
    }
}]);