/**
 * 注册页面调用
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

function replaceDoc()
{
	window.location.replace("login.html");

}
function checkusername(){
	//姓名验证（验证是否已经注册，是否为空）
	//姓名输入框聚焦函数（背景变为白色）
	$(".user_name").focus(function(){
		$(this).css("background-color","#fff");
		});
	//姓名输入框焦点离开函数
	$(".user_name").blur(function(){
		var user_name=$(".user_name").val();//获取姓名输入框内容
		//将姓名中空格去除函数
		function trim(str) {
			return str.replace(/(^\s+)|(\s+$)/g, "");
		}

		user_name=trim(user_name);//调用空格去除函数
		name_length=user_name.length;//字符串长度
		if(name_length<2||name_length>5 ){//输入框为空
			$(".error").html("姓名需2-5个字！");
	 		$(".user_name").css("border-color","red");
	 		$(".zcan").attr("disabled","disabled");
	 		return;
	 	}
		/*$.ajax({
          		type: "post",
           		url: "../../loginCrtl/checkUserName",//从webservices调用，对照，查询是否已经注册
          		data: {username:user_name},// {line:tline,shift:tshift,area:tarea}
           		dataType: "json", 
           		success: function(result) {
           			if (!result.result) {
							$(".error").html("姓名已注册！");
							$(".user_name").css("border-color","red");
							$(".zcan").attr("disabled","disabled");
							return;
					}
				
         	  	},
         	  	error:function(result, status) { //如果没有上面的捕获出错会执行这里的回调函数
             		if (status == 'error') {
                  	  alert("姓名不可用！");
                  	}
           	 	}
           	 });*/

			if(user_name!="" ){//姓名可用
				$(".user_name").css("border-color","#468847");
				$(".error").html("");
				return;
				}
			});
}

function checkmobileNo(){
	//手机号验证
	////手机号输入框聚焦函数（背景变为白色）
	 $(".user_nicheng").focus(function(){
		    $(this).css("background-color","#fff");
		 });
	//手机号输入框焦点离开函数（验证昵称是否输入正确）
	$(".user_nicheng").blur(function(){
			var mobileNo=$(".user_nicheng").val();
		  	//去掉空格
		  	function trim(str) {
					return str.replace(/(^\s+)|(\s+$)/g, "");
			}
		  	var phoneReg =/^1(3|4|5|6|7|8)\d{9}$/;
		  	if(!phoneReg.test(mobileNo)){
		  		$(".error").html("手机号是11个字符！");
				$(".user_nicheng").css("border-color","red");
				$(".zcan").attr("disabled","disabled");
					return;	
		  	}
		  	
		  	/* $.ajax({
      			  type: "post",
       				 url: "../../loginCrtl/checkMobileNo",
      				data: {mobileNo:mobileNo},// {line:tline,shift:tshift,area:tarea}
       				dataType: "json", //返回的类型为XML ，和前面的Json，不一样了
       				 success: function(result) {
    				 		if (!result.result) {
    				 			$(".error").html("此手机号已注册！");
					 			$(".user_nicheng").css("border-color","red");
					 			$(".zcan").attr("disabled","disabled");
					 			return;
    				 		}
     	  		},
     	  		error:function(result, status) { //如果没有上面的捕获出错会执行这里的回调函数
         		   if (status == 'error') {
              	  alert("检查失败！");
              	  return;

            	}
       	 	}
       		
			  
		  });*/

//		if(nicheng_length<8&&nicheng_length>=2 ){	
			$(".user_nicheng").css("border-color","#468847");
			$(".error").html("");
//			return;
//		}

	});
}
function checkemall(){
	//邮箱验证
	
	//邮箱输入框聚焦函数（背景变为白色）
	
	 $(".user_email").focus(function(){
	    	$(this).css("background-color","#fff");
	    	
	 });
	//邮箱输入框失去聚焦函数
	  //验证邮箱是否填写有效	
	  $(".user_email").blur(function(){
			var user_email=$(this).val();
	  		var zhuangtai="yes";

        if(user_email===""){
			 	$(".error").html("邮箱不能为空!");
			 	$(".user_email").css("border-color","red");
			 	$(".zcan").attr("disabled","disabled");
			 	return ;
			 }
        var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
        if(!re.test(user_email)){
        	$(".error").html("邮箱格式不正确!");
		 	$(".user_email").css("border-color","red");
		 	$(".zcan").attr("disabled","disabled");
		 	return ;
		}		 		
	  	/*$.ajax({
 			  type: "post",
 			  url: "../../loginCrtl/checkEmall",
 			  data: {email:user_email},// {line:tline,shift:tshift,area:tarea}
  				dataType: "json", //返回的类型为XML ，和前面的Json，不一样了
  				 success: function(result) {
  					if(!result.result){
  						$(".error").html("此邮箱邮箱已注册<a href='login.html'>点击登录</a>!");
  						$(".user_email").css("border-color","red");
  						$(".zcan").attr("disabled","disabled");
  						return;
  						
  					}
	  		},
   			error:function(result, status) { //如果没有上面的捕获出错会执行这里的回调函数
    		   if (status == 'error') {
         	  alert("邮箱检查失败！");

       	}
  	 	}

  	 	 });*/
	  	
        $(".user_email").css("border-color","#468847");
        $(".error").html("");
	  	
  	});
//		$(".user_email").css("border-color","#468847");
//		$(".error").html("");

}

function checkPassWord(){
	 
	 $(".user_password").focus(function(){
	    	$(this).css("background-color","#fff");
	    	
	  });

	  
	 	//验证密码是否填写有效	
	  $(".user_password").blur(function(){

	  	var user_password=$(".user_password").val();
	  	var length=user_password.length;
	  	var reg = /^[0-9a-zA-Z]+$/;
	 		if(length===0){
	 			$(".error").html("密码不能为空!");
		 		$(".user_password").css("border-color","red");
		 		$(".zcan").attr("disabled","disabled");
			}
	 		if(length!=0){
				if(user_password.length<6||!reg.test(user_password)){
		 			$(".error").html("填写有误!");
		 			$(".user_password").css("border-color","red");
		 			$(".zcan").attr("disabled","disabled");
				}else{
	 			$(".error").html("");
	 			$(this).css("border-color","#468847");
	 		}
	 		}
		  
	  });
}

function register(){
	var user_name=$(".user_name").val();//获取姓名输入框内容
	var mobileNo=$(".user_nicheng").val();
	var user_email=$(".user_email").val();
	var checkCode = $("#checkCode").val();
	var codeKey = $("#codeKey").val();
  	var password=encode($(".user_password").val());
  	
  	

//	password = encode(password);
	var params = {};
	params.username =user_name;
	params.password =password;
	params.mobileNo =mobileNo;
	params.email =user_email;
	params.code =codeKey;
	params.key =checkCode;
	//登录开始
	$.ajax({
		type:"POST",
		url:"../../loginCrtl/register",
		dataTpye:"json",
		data: params,
		success:function(data){
//			if (data.errorCode) {
//				alert(data.errorMessage);
//				window.location = "login.html";
//			}else{
//				Citylift.setCookie("sessionAppkey",data.ObjInfo.operNo);
//				window.location = "index.html";
//			}
			
		},
		error:function(data){
			
			alert("网络错误。。。");
		}
	})
}

function encode(code){
	var result =null;
	if (!code||code.trim() == '') {
		result = '';
	}else{
		result = hex_sha1(code);
	}
	return result;
}
//$("#register").click(register());
$(document).ready(function(){
	getImageCode();
	checkPassWord();
	checkemall();
	checkmobileNo();
	checkusername();
	
	
			
		
});

