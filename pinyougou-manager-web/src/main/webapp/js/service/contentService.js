// 广告管理服务层
pyg.service('contentService',function($http){
	    	
	// 查询所有数据
	this.findAll=function(){
		return $http.get('../content/findAll.do');		
	};
	
	// 根据 ID 查询实体
	this.findOne=function(id){
		return $http.get('../content/findOne.do?id='+id);
	};
	
	// 增加 
	this.add=function(entity){
		return  $http.post('../content/add.do',entity);
	};
	
	// 修改 
	this.update=function(entity){
		return  $http.post('../content/update.do',entity);
	};
	
	// 批量删除
	this.batchDelete=function(selectedList){
		return $http.get('../content/batchDelete.do?ids='+selectedList);
	};
	
	// 多条件分页查询
	this.findPageLimit=function(page,size,searchEntity){
		return $http.post('../content/search.do?page='+page+"&size="+size, searchEntity);
	};
});
