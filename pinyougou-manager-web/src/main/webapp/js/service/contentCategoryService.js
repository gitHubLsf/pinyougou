// 广告分类管理服务层
pyg.service('contentCategoryService',function($http){
	    	
	// 查询所有数据
	this.findAll=function(){
		return $http.get('../contentCategory/findAll.do');		
	};

	// 根据 ID 查询实体
	this.findOne=function(id){
		return $http.get('../contentCategory/findOne.do?id='+id);
	};
	
	// 增加 
	this.add=function(entity){
		return  $http.post('../contentCategory/add.do',entity);
	};
	
	// 修改 
	this.update=function(entity){
		return  $http.post('../contentCategory/update.do',entity);
	};
	
	// 批量删除
	this.batchDelete=function(selectedList){
		return $http.get('../contentCategory/batchDelete.do?ids='+selectedList);
	};
	
	// 多条件分页查询
	this.findPageLimit=function(page,size,searchEntity){
		return $http.post('../contentCategory/search.do?page='+page+"&size="+size, searchEntity);
	};
});
