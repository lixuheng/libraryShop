/*html文件加载完毕后执行*/
$(function() {
	console.log("load over!");
	getXorCode();
	/* 账户框事件 */
	$("#form-account").focusin(function() {
		$(this).parent("div").next("div").children("span").html($(this).attr("default"));
	});
	$("#form-account").focusout(function() {
		$(this).parent("div").next("div").children("span").html("");
		checkUname();
	});
	/*密码框事件*/
	$("#form-pwd").focusin(function() {
		$(this).parent("div").next("div").children("span").html($(this).attr("default"));
		if($(this).val().length>=6){
			$("#form-equalTopwd").parent("div").next("div").children("span").html("");
			$(this).mouseout(function(evt){
				checkPwd();
			})
		}
	});
	$("#form-pwd").focusout(function() {
		$(this).parent("div").next("div").children("span").html("");
		checkPwd();
	});
	$("#form-equalTopwd").focusin(function() {
		$(this).parent("div").next("div").children("span").html($(this).attr("default"));
		if($("#form-pwd").val().length>=6 && $(this).val().length>0 ){
			$("#form-equalTopwd").parent("div").next("div").children("span").html("");
		}
	});
	$("#form-equalTopwd").focusout(function() {
		$(this).parent("div").next("div").children("span").html("");
		checkPwd();
	});
	/*邮箱事件*/
	$("#form-email").focusin(function() {
		$(this).parent("div").next("div").children("span").html($(this).attr("default"));
	});
	$("#form-email").focusout(function() {
		$(this).parent("div").next("div").children("span").html("");
		checkEmail();
	});
	/*手机号事件*/
	$("#form-phone").focusin(function() {
		$(this).parent("div").next("div").children("span").html($(this).attr("default"));
	});
	$("#form-phone").focusout(function() {
		$(this).parent("div").next("div").children("span").html("");
		checkPhone();
	});
	/*图片验证码事件*/
	$("#authCode").focusin(function() {
		$(this).parent("div").next("div").children("span").html($(this).attr("default"));
		$("#authCode").focusout(function() {
			$(this).parent("div").next("div").children("span").html("");
			checkAuthImg();
		});
	});
	/*
	 * 输入事件
	 */
	$('#authCode').on('input propertychange', function() {
	    //console.log($(this).val().length);
	    if($(this).val().length==6){
	    	checkAuthImg();
	    }
	});
	/* 验证方式 */
	$(".orEmail>a").click(function(){
		$(".item-email-wrap").removeAttr("hidden");
		$(".item-phone-wrap").attr("hidden","");
		$(".form-item-phonecode").attr("hidden","");
		$("#method").val("email");
	})
	$(".orPhone>a").click(function(){
		$(".item-email-wrap").attr("hidden","");
		$(".item-phone-wrap").removeAttr("hidden");
		$(".form-item-phonecode").removeAttr("hidden");
		$("#method").val("phone");
	})
	/*获取手机短信验证码*/
	$("#getPhoneCode").click(function(){
		getPhoneSms();
	});
	/*校验手机短信*/
	$("#phoneCode").focusin(function() {
		$(this).parent("div").next("div").children("span").html($(this).attr("default"));
		$("#phoneCode").focusout(function() {
			$(this).parent("div").next("div").children("span").html("");
			checkPhoneSms();
		});
	});
	
	$('#phoneCode').on('input propertychange', function() {
	    //console.log($(this).val().length);
	    if($(this).val().length==4){
	    	checkPhoneSms();
	    }
	});
	/*$('#phoneCode').keydown(function(evt) {
	    console.log(evt.which);
	});*/
	
	
	/* 校验总输入 */
	$("#submit").click(function(){
		bool1 = vpsms&&vu&&vp&&va;
		bool2 = ve&&vu&&vp&&va;
		//alert($(".form-agreen input").get(0).checked);
		//alert($(".form-agreen input").is(':checked'));
		//$('#checkbox-id').attr('checked'))
		if(!$(".form-agreen input").is(':checked')){
			$(".form-agreen input").parent().next("div").children("span").html("<font color='red'>请先阅读协议，并同意协议！</font>")
			return false;
		}
		console.log("submit "+bool1+" "+bool2);
		var rs = false;
		if($("#method").val()=="phone"&&bool1){
			//$("regist-form").submit();
			rs = true;
		}else if(bool2){
			//$("regist-form").submit();
			rs = true;
		}else{
			checkUname();
			checkPwd();
			checkAuthImg();
			if($("#method").val()=="phone")
				checkPhone();
			else
				checkEmail();
			return false;
		}
		
		//密码处理
		var rand = $("#pwdRand").text();
		oldpwd = $("#form-pwd").val();
		var pwdRs = new Array();
		pwdRs = xor(oldpwd,rand);
		newpwd = Bytes2Str(pwdRs);
		$("#form-pwd").val(newpwd);
		
		
		if(rs){
			loadData("regist",$("#regist-form").serialize()+"&local="+storage.getItem("IPTOLocal"),function(data){
				console.log(data);
				if(data.code=="c1"){
					if($("#method").val()=="phone"){
						window.wxc.xcConfirm('<img src="static/lib/xcConfirm/img/ok.png" class="img-responsive" alt="ok"><span class="font20">注册成功，1秒自动跳转到首页...</span>');
						setTimeout(function(){window.location = storage.getItem("basePath") +"/index";},1000);
					}else{
						window.wxc.xcConfirm('<img src="static/lib/xcConfirm/img/ok.png" class="img-responsive" alt="ok"><span class="font20">注册成功,请到查收并验证邮箱后登录...</span>');
					}
					return true;
				}else{
					window.wxc.xcConfirm("注册失败","error");
					getXorCode();
					$(".form-item input[type=password]").val("");
					return false;
				}
			},"json","POST");
		}
	});
	
	/*var rand = "abcdefgh";
	console.log(rand);
	var userpwd = "123456";
	var pwdRs = new Array();
	pwdRs = xor(userpwd,rand);
	//var bytes = [48,49,50,91];
	var hex = Bytes2Str(pwdRs);
	console.info("hex-"+hex);
	console.info("pwdRs="+pwdRs);
	newrs ="";
	for(i=0;i<pwdRs.length;i++){
		newrs+=pwdRs[i];
	}
	pwdRs2 = xor(newrs,rand);
	console.info("pwdRs2="+pwdRs2);*/
	
	
		
})//ready go


/*工具函数定义*/
var vph=false;
function checkPhone(){
	len = $("#form-phone").val().length;
	//console.log("len="+len)
	if(len==0) return ;
	if((len<3&&len>0) ){ 
		$("#form-phone").parent("div").next("div").children("span").html("<font color='red'>手机号位数不正确！</font>")
		vph = false;
		return false;
	}
	if(!(/^1[34578]\d{9}$/.test($("#form-phone").val())) ){
		$("#form-phone").parent("div").next("div").children("span").html("<font color='red'>手机号填写不正确！</font>")
		vph = false;
		return false;
	}
	else{
		loadData("checkPhone",$("#form-phone").serialize(),function(data){
			console.log(data);
			if(data.code=="c1"){
				$("#form-phone").parent("div").next("div").children("span").html("<font color='red'>该手机号已经被注册，请尝试登陆！</font>")
				vph = false;
			}else{
				vph =true;
			}
		});
    }
}

var vu=false;
function checkUname(){
	len = $("#form-account").val().length;
	//console.log("length="+len);
	if(len==0) return;
	if(len<4&&len>0){
		$("#form-account").parent("div").next("div").children("span").html("<font color='red'>长度不够用啊！</font>")
		vu = false;
		return false;
	}
	var pattern = new RegExp("[`~!@%#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	if(pattern.test($("#form-account").val())){
		$("#form-account").parent("div").next("div").children("span").html("<font color='red'>含有特殊字符！</font>")
		vu = false;
		return false;
	}
	loadData("checkUname",$("#form-account").serialize(),function(data){
		console.log(data);
		if(data.code=="c1"){
			$("#form-account").parent("div").next("div").children("span").html("<font color='red'>该用户名已被占用！</font>")
			vu = false;
		}else{
			vu = true;
		}
	});	

}

var ve=false;
function checkEmail(){
	if($("#form-email").val().length==0) return;
	reg=/^[A-Za-z0-9_\-]+@[A-Za-z0-9_\-]+\.[A-Za-z0-9_\-]+$/;
	if(!reg.test( $("#form-email").val()) ){
		$("#form-email").parent("div").next("div").children("span").html("<font color='red'>邮箱格式不对！</font>");
		ve = false;
		return ;
	}else{
		loadData("checkEmail",$("#form-email").serialize(),function(data){
			console.log(data);
			if(data.code=="c1"){
				$("#form-email").parent("div").next("div").children("span").html("<font color='red'>该邮箱已被占用，可以尝试登陆！</font>")
				ve = false;
			}else{
				ve = true;
			}
		},"json","get");
	}
}

var vp=false;
function checkPwd(){
	len = $("#form-pwd").val().length;
	if(len<6&&len>0){
		$("#form-pwd").parent("div").next("div").children("span").html("<font color='red'>密码长度不够！</font>");
		vp = false;
	}
	else{
		$("#form-pwd").parent("div").next("div").children("span").html("");
		vp = true;
	}
	if($("#form-pwd").val()!==$("#form-equalTopwd").val()){
		$("#form-equalTopwd").parent("div").next("div").children("span").html("<font color='red'>两次密码输入不一致！</font>")
		vp = false;
	}
}

var va=false;
function checkAuthImg(){
	len = $("#authCode").val().length;
	if(len==0) return ;
	console.log(len);
	if(len==6){
		loadData("imgvalid/checkAuthImg",$("#authCode").serialize(),function(data){
			console.log(data);
			if(data.code=="c1"){
				$("#authCode").parent("div").next("div").children("span").html("<font color='green'>填写正确！</font>")
				va = true;
			}else{
				$("#authCode").parent("div").next("div").children("span").html("<font color='red'>填写错误！</font>")
				va = false;
			}
		},"json","POST");
	}else{
		$("#authCode").parent("div").next("div").children("span").html("<font color='red'>填写错误！</font>")
		va = false;
	}
}

var gvpsms = false;
function getPhoneSms(){
	checkPhone();checkAuthImg();
	if(!vph){return false;}
	if(!va){return false;}
	$("#getPhoneCode").attr('disabled',true);
	$("#getPhoneCode").css("backgroud-color","gray");
	var time = 30;
	var intervalid = setInterval(function(){
		if(time>=0){
			$("#getPhoneCode").text(time+" 秒");
			--time;
		}else{
			$("#getPhoneCode").attr('disabled',false);
			$("#getPhoneCode").css("backgroud-color","white");
			$("#getPhoneCode").text('重新获取验证码');
			clearInterval(intervalid);
		}
	},1000);
	loadData("sms/getCode","",function(data){
		console.log("sms/getCode "+data);
		if(data.code=="c1"){
			console.log("获取成功");
			gvpsms = true;
		}else{
			gvpsms = false;
			alert("系统故障");
		}
	},"json","get");
}


var vpsms = false;
function checkPhoneSms(){
	if(!gvpsms){return false;}
	loadData("sms/checkCode",$("#phoneCode").serialize(),function(data){
		console.log(data);
		if(data.code=="c1"){
			$("#phoneCode").parent("div").next("div").children("span").html("<font color='green'>恭喜，填写正确！</font>")
			vpsms = true;
		}else{
			$("#phoneCode").parent("div").next("div").children("span").html("<font color='red'>短信验证码填写错误！</font>")
			vpsms = false;
		}
	},"json","post");
}



function getXorCode(){
	//pwdRand
	loadData("getXorCode","",function(data){
		if(data.code=='c1'){
			$("#pwdRand").text(data.list[0]);
		}else{
			
		}
	},"json","post","false")
}
