/**
 * 
 */

var sessionAppkey = Citylift.getCookie("sessionAppkey");
$("#userName").html(sessionAppkey);
//alert(sessionAppkey);

var method="GET";
var url ="http://www.mxnzp.com/api/holiday/single/20181121";
var params={};
var callback;
var aa = Citylift.request(method,url,params,callback);
//alert(aa.success);
//导入文件
function importExp() { 
	var formData = new FormData(); 
	var name = $("#upfile").val(); 
	formData.append("file",$("#upfile")[0].files[0]); 
	formData.append("name",name); 
	$.ajax({ 
		url : '../../excel/upload', 
		type : 'POST', 
		async : false, 
		data : formData, // 告诉jQuery不要去处理发送的数据
        processData : false, // 告诉jQuery不要去设置Content-Type请求头
        contentType : false, 
        beforeSend:
        	function(){ 
        	console.log("正在进行，请稍候"); 
        	}, success : function(responseStr) { 
        		if(responseStr=="01"){ 
        			alert("导入成功"); 
        			}else{ 
        				alert("导入失败"); 
        				} 
        		} 
        	}); 
	}



//导入文件
function exportExcel() { 
	window.location="../../excel/exportExcel";
	}
