$(document).ready(function(){
	//文本变化前的内容，使用它和新内容对比，发生了变化才发送ajax请求
	var oldContent = "";
	$("#content").keyup(function(){
		var content = $.trim($("#content").val());
		if(content == "" || content == oldContent){
			return;//没有输入内容或者文本内容没有发生变化时就返回
		}
		oldContent = content;
		var params = {"input":$.trim($("#content").val())};//{"input":"北"}   json类型的数据 
		//ajax被jquery封装了，意味着只需要设置几个参数就可以了。
		$.ajax({
			url:"../../search/getKeyword",//要访问的action
			type:"post",//数据提交方式
			data:params,//传到服务器的数据
			dataType:"json",//传到服务器的数据类型
			success:function(data){   //后台执行成功后要执行的函数     回调函数
				//alert(JSON.stringify(data));
//				alert(data[1]);
				if(data.keywords.length == 0){//没有得到返回值
					//没有关键字就隐藏提示框，并返回
					$("#suggest").hide();
					return;
				}
				$("#suggest").show().html("");//显示提示框并清空提示框
				//添加关键字列表
				for(var i=0;i<data.keywords.length;i++){
					$("#suggest").append("<div class='keyword'>"+data.keywords[i]+"</div>");
				}
				//为新添加的关键字列表添加事件
				$(".keyword").live("mousemove",function(event){
					//鼠标移入背景变成灰色
				$(event.target).css("background-color","silver");
				});
				$(".keyword").live("mouseout",function(event){
					//鼠标移出背景变成白色
				$(event.target).css("background-color","white");		
				});
				$(".keyword").live("dblclick",function(event){
					//双击时把内容加入到输入框
					$("#content").val($(event.target).html());
					$("#suggest").hide();
					oldContent = "";
				});
			},
			error:function(){     //后台执行不成功，或者没有接受到后台的值
				alert("Ajax请求出错");
			}
		});
	});
});




