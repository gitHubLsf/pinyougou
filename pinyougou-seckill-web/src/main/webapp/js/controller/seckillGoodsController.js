// 秒杀商品控制层
pyg.controller('seckillGoodsController', function ($scope,
                                                   $location,
                                                   seckillGoodsService,
                                                   $interval) {

    // 查询正在参与秒杀的商品列表
    $scope.findList = function () {
        seckillGoodsService.findList().success(
            function (response) {
                $scope.list = response;
            }
        );
    };


    // 查询正在参与秒杀的某个商品
    $scope.findOneFromCache = function () {
        // 获取商品 id
        var id = $location.search()['id'];

        seckillGoodsService.findOneFromCache(id).success(
            function (response) {
                if (response === '') {
                    // 如果找不到
                    alert("该商品未参与秒杀");
                    // 跳转到秒杀频道页面
                    location.href = 'seckill-index.html';
                } else {
                    $scope.entity = response;

                    // 倒计时
                    // 计算截止时间的时间戳
                    var end = new Date($scope.entity.endTime).getTime();
                    // 计算当前时间的时间错
                    var current = new Date().getTime();
                    // 计算倒计时的总秒数
                    var allSecond = (end - current) / 1000;

                    if (allSecond <= 0) {
                        alert("该商品的秒杀活动已结束");
                        // 跳转到秒杀频道页
                        location.href = 'seckill-index.html';
                        return;
                    }

                    // 将总秒数转换为 x天:y时:z分:m秒 形式
                    $scope.time = convertTimeToString(allSecond);

                    // 执行间接性任务，将总秒数 allSecond 减一
                    var task = $interval(function () {
                        allSecond = allSecond - 1;
                        if (allSecond <= 0) {
                            // 取消间接性任务
                            $interval.cancel(task);
                            alert("该商品的秒杀活动已结束");
                            // 跳转到秒杀频道页
                            location.href = 'seckill-index.html';
                        } else {
                            // 将总秒数转换成时间字符串
                            $scope.time = convertTimeToString(allSecond);
                        }
                    }, 1000);
                }
            }
        );
    };


    // 一天的总秒数
    var daySecond = 24 * 60 * 60;
    // 一小时的总秒数
    var hourSecond = 3600;

    convertTimeToString = function (allSecond) {
        // 计算天数
        var day = Math.floor(allSecond / daySecond);
        // 计算小时数
        var hour = Math.floor((allSecond - day * daySecond) / hourSecond);
        // 计算分钟数
        var minute = Math.floor((allSecond - day * daySecond - hour * hourSecond) / 60);
        // 计算秒数
        var second = Math.floor(allSecond - day * daySecond - hour * hourSecond - minute * 60);
        // 转成字符串
        var timeString = '';

        if (day > 0) {
            timeString = timeString + day + ' 天 ';
        }

        if (hour >= 0 && hour < 10) {
            timeString = timeString + '0';
        }
        timeString = timeString + hour + ' 时 ';


        if (minute >= 0 && minute < 10) {
            timeString = timeString + '0';
        }
        timeString = timeString + minute + ' 分 ';

        if (second >= 0 && second < 10) {
            timeString = timeString + '0';
        }
        timeString = timeString + second + ' 秒 ';

        return timeString;
    };
});