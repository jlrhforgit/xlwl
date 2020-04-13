

/**
 * 预览图片
 */
function imgPreview(fileDom) {
	//判断是否支持FileReader
	if(window.FileReader) {
		var reader = new FileReader();
	} else {
		alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
	}
	//获取文件
	var file = fileDom.files[0];
	var imageType = /^image\//;
	//是否是图片
	if(!imageType.test(file.type)) {
		alert("请选择图片！");
		return;
	}
	//读取完成
	reader.onload = function(e) {
		//获取图片dom
		var img = document.getElementById("preview");
		//图片路径设置为读取的图片
		img.src = e.target.result;
	};
	reader.readAsDataURL(file);
}

/**
 * 上传图片
 * @returns
 */
function uploadImg(){
	var formData = new FormData(); 
	formData.append('file', $('#input_file')[0].files[0]);  //添加图片信息的参数
	formData.append('sizeid',123);  //添加其他参数
	$.ajax({
	    url: '/material/uploadFile',
	    type: 'POST',
	    cache: false, //上传文件不需要缓存
	    data: formData,
	    processData: false, // 告诉jQuery不要去处理发送的数据
	    contentType: false, // 告诉jQuery不要去设置Content-Type请求头
	    success: function (data) {
	        var rs = eval("("+data+")");
	        if(rs.state==1){
	            tipTopShow('上传成功！');
	        }else{
	            tipTopShow(rs.msg);
	        }
	    },
	    error: function (data) {
	        tipTopShow("上传失败");
	    }
	})  

}


//用于进行图片上传，返回地址
function setImg(obj){
	var f=$(obj).val();
	console.log(obj);
    if(f == null || f ==undefined || f == ''){
        return false;
    }
    if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(f))
    {
        alert("类型必须是图片(.png|jpg|bmp|gif|PNG|JPG|BMP|GIF)");
        $(obj).val('');
        return false;
    }
    var data = new FormData();
    console.log(data);
    $.each($(obj)[0].files,function(i,file){
        data.append('file', file);
    });
    console.log(data);
    $.ajax({
        type: "POST",
        url: "../../file/uploadImg",
        data: data,
        cache: false,
        contentType: false,    //不可缺
        processData: false,    //不可缺
        dataType:"json",
        success: function(ret) {
        	console.log(ret);
            if(ret.code==0){
                    $("#photoUrl").val(ret.result.url);//将地址存储好
                    $("#photourlShow").attr("src",ret.result.url);//显示图片   
//                    alertOk(ret.message);
            }else{
//                alertError(ret.message);
                $("#url").val("");
                $(obj).val('');
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("上传失败，请检查网络后重试");
        }
    });
}

