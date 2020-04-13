/**
 * 登录页面调用
 */


function getImageCode(){
	$.ajax({
		type:"GET",
//		url:"http://127.0.0.1:8080/xlws/imageCode/getImageCode2",
		url:"../../imageCode/getImageCode2",
		dataTpye:"json",
		success:function(data){
			$("#checkCode").val(data.imageKey);
			$("#checkCode").attr("src",'data:image/png;base64,'+data.imageCode);
			
		},
		error:function(data){
			
			alert("网络错误。。。");
		}
	})
}

$(document).ready(function(){
	getImageCode();
});

$("#operNo").val("000001");
$("#password").val("000000");

function doLogin(){
	// ===== 简易登录校验 ======
	
	var operNo = $("#operNo").val();
	if (!operNo || "" == operNo.trim()) {
		$('#operNo').val("");
		alert("请输入操作员编号！");
		return;
	}
	
	var password = $("#password").val();
	if (!password || "" == password.trim()) {
		$('#password').val("");
		alert("请输入密码！");
		return;
	}
	if (password.length < 6) {
		$('#password').val("");
		alert("请输入6位以上密码！");
		return;
	}
	var codeKey = $("#codeKey").val();
	if (!codeKey || "" == codeKey.trim()) {
		$('#codeKey').val("");
		alert("请输入验证码！");
		return;
	}
	if (codeKey.length != 4) {
		$('#codeKey').val("");
		alert("验证码位数不对，请重新输入！");
		return;
	}
	var checkCode = $("#checkCode").val();
	password = encode(password);
	var params = {};
	params.operNo =operNo;
	params.password =password;
	params.code =codeKey;
	params.key =checkCode;
	//登录开始
	$.ajax({
		type:"POST",
		url:"../../loginCrtl/login",
		dataTpye:"json",
		data: params,
		success:function(data){
			if (data.errorCode) {
				alert(data.errorMessage);
				window.location = "login.html";
			}else{
				Citylift.setCookie("sessionAppkey",data.ObjInfo.operNo);
				window.location = "index.html";
			}
			
		},
		error:function(data){
			
			alert("网络错误。。。");
		}
	})
	
	
	
}
/**
 * 密码明文加密
 * @param code
 * @returns
 */

function encode(code){
	var result =null;
	if (!code||code.trim() == '') {
		result = '';
	}else{
		result = hex_sha1(code);
	}
	return result;
}
/**
 * 回车登录
 * @param e
 * @returns
 */
// 如果是添加到body元素上，即使失去焦点，回车也可以触发keyup事件 
document.body.addEventListener('keyup', function (e) { 
//	console.log(e) ;
	if (e.keyCode == '13') { 
		doLogin();
		} 
	});

