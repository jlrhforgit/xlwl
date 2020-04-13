/**
 * 自定义js , 各种通用api接口调用， 操作cookie.
 */
if (!window.Citylife) {
	window.Citylife={};
}

Citylift=function(){
	var PATH ='service';
	var HOST = 'http://www.mxnzp.com/api';
	
	
	
	var api = {};
	
	var setCookie =function(name,value){
		
		document.cookie = name + "="+ escape(value);
		
	}
	api.setCookie = setCookie;
	
	
	var getCookie = function(name){
		var arr,reg = new RegExp("(^|)"+name+"=([^;]*)(;|$)");
		if (arr=document.cookie.match(reg)) {
			return unescape(arr[2]);
		}else return null;
	}
	
	api.getCookie = getCookie;
	var delCookie = function delCookie(name){
		var exp = new Date();
			exp.setTime(exp.getTime() - 1);
		var cval = getCookie(name);
		if (cval!=null) {
			document.cookie =name+"="+cval+";expires="+exp.toGMTString();
		}
	}
	
	api.delCookie = delCookie;
	
	/**
	 * 获取指定日期的节假日及万年历信息
	 */
	var getHoliday = function(value){
		var uri = '/holiday/single/'+value;
		
	}
	
	api.getHoliday = getHoliday;
	
	var request = function(method,url,params,callback){
		var option;
		if ($.isPlainObject(callback)) {
			option = callback.option;
		}
		if (params) { //清除掉没有值的字段
			$.each(params,function(key,value){
				if (value === null || value === undefined) {
					delete params[key];
				}
			});
		}
//		beforeRequest(casllback);
		return $.ajax({
			url:url,
			data:params,
			type:method,
			cache:false,
			traditional:true,
			success:function(data,testStatus,jqXHR){
				if (jqXHR.responseTest =='') {
					data = '';
				}
			},error:function(data){
				
			}
		});
	}
	api.request = request;
	
	
	return api;
}();