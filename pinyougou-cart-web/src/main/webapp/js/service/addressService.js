// 地址管理服务层
pyg.service('addressService', function ($http) {

    // 查询当前在线用户所有的地址
    this.findAddressListByUserName = function () {
        return $http.get('address/findAddressListByUserName.do');
    };


    // 添加地址
    this.addAddress = function (address) {
        return $http.post('address/addAddress.do', address);
    }

});
