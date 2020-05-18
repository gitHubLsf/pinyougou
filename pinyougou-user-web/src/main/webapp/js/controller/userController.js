// 用户中心控制层
pyg.controller('userController', function($scope,
										  userService){


	// 根据 ID 查询实体 
	$scope.findOne=function(id){				
		userService.findOne(id).success(
			function(response){
				// 将查询到的数据显示在修改框中
				$scope.entity= response;					
			}
		);				
	};
	
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=userService.update( $scope.entity ); //修改  
		}else{
			serviceObject=userService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	};
	

	// 用户注册
	$scope.register = function () {
		// 判断两次输入的密码是否一致
		if ($scope.password !== $scope.user.password) {
			alert("两次输入的密码不一致!");

			// 清空
			$scope.password = $scope.user.password = "";

			return;
		}

		// 到后台注册
		userService.add($scope.user, $scope.smsCode).success(
			function (response) {
				alert(response.message);
			}
		);
	};


	// 绑定用户信息
	$scope.user = { phone: "" };
	// 发送短信验证码
	$scope.sendSmsCode = function () {
		// 判断用户是否输入手机号
		if ($scope.user.phone === "") {
			alert("请输入手机号!");
			return;
		}

		// 去后台获取发送短信验证码
		userService.sendSmsCode($scope.user.phone).success(
			function (response) {
				alert(response.message);
			}
		);
	}

});	
