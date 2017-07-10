
function getXorCode(){
	//pwdRand
	loadData("getXorCode","",function(data){
		if(data.code=='c1'){
			$("#pwdRand").text(data.list[0]);
		}else{
			
		}
	},"json","post","false")
}

//退格记录
var backspace = new Array();
backspace[0] = 0;
backspace[1] = 0;

/*工具函数定义*/
var pwd = false;
function checkPwd(){
	if( $("input[type=password]").val().length>0 )
	{
		if( $("input[type=password]").val().length< 6){
			$("input[type=password]").next("span").html("<font color='red'>密码不规范</font>");
			pwd =false;
		}
		else{pwd =true;}
	}
}

var passErrornum =0 ;
var va=false;
function checkAuthImg(){
	var len = $(".form_imgCode>input").val().length;
	if(len==0) return ;
	console.log(len);
	if(len==6){
		loadData("imgvalid/checkAuthImg",$(".form_imgCode>input").serialize(),function(data){
			console.log(data);
			if(data.code=="c1"){
				$(".form_imgCode span").html("<font color='green'>填写正确！</font>")
				va = true;
			}else{
				$(".form_imgCode span").html("<font color='red'>填写错误！</font>")
				va = false;
			}
		},"json","POST");
	}else{
		$(".form_imgCode span").html("<font color='red'>填写错误！</font>")
		va = false;
	}
}
var vacc= false;
function checkAccount(){
	var obj = $(".form_uname>input");
	var len = obj.val().length;
	if(len==0) return;
	if(len>0&&len<4) { obj.next("span").html("<font color='red'>长度错误！</font>"); return;}
	var pattern = new RegExp("[`~!%#$^&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]");
	if(pattern.test(obj.val())){
		 obj.next("span").html("<font color='red'>用户名不符合规范！</font>")
		 return;
	}
	loadData("checkAccount",obj.serialize(),function(data){
		console.log(data);
		if(data.code=="c1"){
			$("#loginMethod").val(data.list[0]);
			vacc = true;
		}else{
			obj.next("span").html("<font color='red'>用户名不符合约定！</font>")
			vacc = false;
		}
	},"json","POST");
	
}


function stop(event){
	alert("preventDefault!");
	var event = event || window.event;
	event.preventDefault(); // 兼容标准浏览器
	window.event.returnValue = false; // 兼容IE6~8
}

/******************执行登录********/
function doLogin(event){
	if($("#haveImgCode").val()=="true"){
		if(!va) {
			checkAuthImg();
			//stop(event); 
			return false;
		}
	}
	if(!vacc) {
		checkAccount();
		//stop(event);
		return false;
	}
	if(!pwd) {
		checkPwd();
		return;
	}
	//密码处理
	var rand = $("#pwdRand").text();
	//console.info("rand-"+rand);
	oldpwd = $("#form-pwd").val();
	//console.info("oldpwd-"+oldpwd);
	var pwdRs = new Array();
	pwdRs = xor(oldpwd,rand);
	newpwd = Bytes2Str(pwdRs);
	//console.info("newpwd-"+newpwd);
	$("#form-pwd").val(newpwd);
	//表单基本参数
	var requestParams= $("#login-form").serialize();
	//附加额外信息
	requestParams += "&pwdErrorNum="+passErrornum;
	requestParams += "&backspace="+backspace;
	console.info("requestParams-"+requestParams);
	$.ajax(
			{
				url:"login",
				type:"POST",
				dataType:"json",
				data:requestParams
			}
	)
	.fail(function(data){console.log(data)})
	.done(function(data){
		console.log(data);
		if(data.code=="c1"){
			window.wxc.xcConfirm('<img src="static/lib/xcConfirm/img/ok.png" class="img-responsive" alt="ok"><span class="font20">登录成功，1秒自动跳转到首页...</span>');
			setTimeout(function(){window.location = storage.getItem("basePath")+"/index";},1000);
			return true;
		}else{//data.code=="c0"
			//先获取一下
			getXorCode();
			$("#form-pwd").val("");
			$(".form_imgCode>img").attr("src",storage.getItem("basePath")+"/imgvalid/getImgCode?time="+new Date().getTime());
			passErrornum++;
			if(passErrornum>=3){
				$(".form_imgCode").removeAttr("hidden");
				$("#haveImgCode").val("true");
				return false;
			}
			if(data.list!=null&&data.list[0]!=null){
				window.wxc.xcConfirm("你的注册步骤，还需验证邮箱，请及时验证","error");
				//setTimeout(function(){window.location = basePath+"/index"+data.list[0];},1000);
			}
			$(".form_pwd>span").html("<font color='red'>用户名或密码错误！</font>")
			//stop(event);
			return false;
		}
	})
}

$(function(){
	//先获取一下
	getXorCode();
	$(".form_imgCode span").html("<font color='black'>看不清，点击图片更换！</font>")
	$(".form_uname>input").focusin(function(){
		$(this).next("span").html("");
		$(this).focusout(function(){
			checkAccount();
		});
	});
	
	/*$(".form_imgCode>input").focusin(function() {
		$(".form_imgCode>input").focusout(function() {
			if($(this).val().length>0)
			{checkPhoneSms();}
		});
	});
	*/
	
	//监听图片验证码输入事件
	$('.form_imgCode>input').on('input propertychange', function() {
	    if($(this).val().length==6){
	    	checkAuthImg();
	    }
	});
	
	$("input[type=password]").focusin(function() {
		$("input[type=password]").next("span").html("")
		$(this).focusout(function() {
			checkPwd();		
		});
	});
	
	
	//监听按键
	$("input[type=password]").on("keydown",function(evt){
		if(evt.which==13){
			checkPwd();
			console.log("submit begin");
			doLogin();
		}
		if(evt.which == 8 ){
			++backspace[1];
			console.info("backspace[1]"+backspace[1]);
		}
	});
	
	
	$('input[name=account]').on('keydown', function(evt) {
	    if(evt.which == 8){//退格键
	    	++backspace[0];
	    	console.info("backspace[0]"+backspace[0]);
	    }
	});
	
	$("#submit").click(function(event){
		console.log("submit begin");
		doLogin(event);
	});
	
})



