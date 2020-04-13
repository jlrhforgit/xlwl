/**
 * 
 */
function sendmail(){
	var tomail = $("#tomail").val();
	var title = $("#title").val();
	var mailContent = $("#content").val();
	var data ={};
	data.toMail =tomail;
	data.title =title;
	data.mailContent =mailContent;
	$.ajax({
		  type: 'POST',
		  url: '../../email/sendMail',
		  datatype:'json',
		  data: data,
		  success: function(data){
			  alert(data.result);
		  },
		 
		});

}

$("#sendmail").click(function(){
	sendmail();
})
