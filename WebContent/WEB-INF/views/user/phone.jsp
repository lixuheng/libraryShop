<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../head.jsp"%>
<title>手机验证</title>
</head>
<body>

	<section class="bigbox">
		<div hidden id="mode">${mode }</div>
		<div id="phoneBox" >
			<div id="head" class="contentM10">
				<h4 id="title">更换手机</h4>
			</div>
			<div id="main" class="contentM20">
				<form id="phoneForm">
					<div class="old">
						<label>已验证的手机号:</label>
						<input name="phone" id="oldPhone" placeholder="已验证的手机号" class="form-control"/>
					</div>
					<div class="input-tip old">
					    <span></span>
					</div>
					<div >&nbsp;</div>
					<div class="new">
						<label>新的手机号:</label>
						<input name="phone" id="newPhone" placeholder="新的手机号" class="form-control"/>
					</div>
					<div class="input-tip new">
					    <span></span>
					</div>
					<div >&nbsp;</div>
					<div class="form-item">
						<div class="getcode">
							<button  id="getOldCode" class="btn btn-default old" type="button">点击为当前绑定手机获取验证码</button>
							<button  id="getNewCode" class="btn btn-default new" type="button">点击为新手机获取验证码</button>
							<br/>
							<div class="old">
								<a href="javascript:;">&nbsp;已验证手机无法接收短信？</a>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="old">
							<label>请输入当前绑定手机验证码:</label>
							<input type="text" name="code" maxlength="6"
								id="oldPhoneCode" class="form-control" placeholder="请输入当前绑定手机验证码"
								autocomplete="off" />
							<i class="i-status"></i>
						</div>
						<div class="input-tip old">
					    	<span></span>
						</div>
						<div>&nbsp;</div>
						<div  class="new">
							<label>请输入新手机验证码:</label>
							<input type="text" name="code" maxlength="6"
								id="newPhoneCode" class="form-control" placeholder="请输入新手机验证码"
								autocomplete="off" />
							<i class="i-status"></i>
						</div>
						<div class="input-tip new">
					    	<span></span>
						</div>
					</div>
					<div><input type="button" id='ensure' class='btn btn-info' value='确认'/></div>
					<div >&nbsp;</div>
					<div id="show"></div>
				</form>
			</div>
		</div>
	</section>
	</body>
<style type="text/css">
.bigbox{
	width:600px;
	margin:0px auto;
	margin-top:60px;
}
#phoneBox{
	width:560px;
	min-height:400px;
	border:solid 1px #ddd;
	background: #efedfe;
}
#main {
	width:500px;
}
label{
	color:#789789;
}
</style>
	<script type="text/javascript" charset="utf8">
		var mode = $("#mode").text();
		//mode ="change";
		//mode ="checkOld";
		/*数据初始化*/
		if(mode=="change"){
			$("#title").text("更换手机");
			$(".new").hide();
		}else if(mode=="checkNew"){
			$("#title").text("新增手机验证");
			$(".old").hide();
		}else if(mode=="checkOld"){
			$("#title").text("验证手机号");
			$(".new").hide();
		}else{
			alert("发生错误，请关闭本页面");
		}
		
		/*校验事件*/
		$("#oldPhone").focusout(function(){
			checkPhone("#oldPhone");
		})
		$("#oldPhone").mouseout(function(){
			checkPhone("#oldPhone");
		})
		$("#newPhone").focusout(function(){
			checkPhone("#newPhone");
		})
		$("#newPhone").mouseout(function(){
			checkPhone("#newPhone");
		})
		
		/*获取短信验证事件*/
		$("#getOldCode").click(function(){
			if(oldB){
				getPhoneSms("#getOldCode");
			}else{
				checkPhone("#oldPhone");
			}
			
		})
		
		$("#getNewCode").click(function(){
			if(newB){
				getPhoneSms("#getNewCode");
			}else{
				checkPhone("#newPhone");
			}
		})
		
		
		
		/*输入短信验证校验事件*/
		$('#oldPhoneCode').on('input propertychange', function() {
		    if($(this).val().length==4){
		    	checkPhoneSms('#oldPhoneCode');
		    }
		});
		
		$('#newPhoneCode').on('input propertychange', function() {
		    if($(this).val().length==4){
		    	checkPhoneSms('#newPhoneCode');
		    }
		});
		
		/*确认*/
		$("#ensure").click(function(){
			var isok = null;
			/* var vpsmsNew = false;
			var vpsmsOld = false; */
			if(mode=="change"){
				isok = vpsmsNew&&vpsmsOld;
			}else if(mode=="checkNew"){
				isok=vpsmsNew;
			}else if(mode=="checkOld"){
				isok=vpsmsOld;
			}
			if(isok){
				loadData("user/phone",'mode='+mode,function(data){
					if(data.code == "c1"){
						window.wxc.xcConfirm("成功","success");
					}else{
						window.wxc.xcConfirm("失败","error");
					}
				},"json","post")
			}else{
				window.wxc.xcConfirm("短信验证不正确","error");
			}
			
		})
		
		
		/*检查手机号的正确性，传入参数是手机号input的id，附带两个标识*/
		var oldB =false;
		var newB =false;
		function checkPhone(id){
			len = $(id).val().length;
			//console.log("len="+len)
			if(len==0) return ;
			if((len<3&&len>0) ){ 
				$(id).parent("div").next("div").children("span").html("<font color='red'>手机号位数不正确！</font>")
				vph = false;
				return false;
			}
			if(!(/^1[34578]\d{9}$/.test($(id).val())) ){
				$(id).parent("div").next("div").children("span").html("<font color='red'>手机号填写不正确！</font>")
				vph = false;
				return false;
			}
			else{
				url = "checkPhone";				
				if(id=="#oldPhone"){
					url = "user/checkPhone";
				}
				$(id).parent("div").next("div").children("span").html("")
				//检查手机号是否已经存在
				loadData(url,$(id).serialize(),function(data){
					console.log(data);
					if(data.code=="c1"){//手机号已经存在
						if(id=='#newPhone'){
							newB = false;
							$(id).parent("div").next("div").children("span").html("<font color='red'>该手机号已经被注册！</font>")
						}
						if(id=='#oldPhone'){
							oldB =true;
							$(id).parent("div").next("div").children("span").html("")
						}
					}else{//手机号不存在
						if(id=='#newPhone'){
							newB = true;
							$(id).parent("div").next("div").children("span").html("")
						}
						if(id=='#oldPhone'){
							oldB = false;
							$(id).parent("div").next("div").children("span").html("<font color='red'>该手机号不存在，无法验证！</font>")
						}
						
					}
				},"json","get");
		    }
		}
		
		//获取短信验证码，附带两成功与否的标志
		var gvpsmsOld = false;
		var gvpsmsNew = false;
		function getPhoneSms(id){
			$(id).attr('disabled',true);
			$(id).css("backgroud-color","gray");
			var time = 30;
			var intervalid = setInterval(function(){
				if(time>=0){
					$(id).text(time+" 秒");
					--time;
				}else{
					$(id).attr('disabled',false);
					$(id).css("backgroud-color","white");
					$(id).text('重新获取验证码');
					clearInterval(intervalid);
				}
			},1000);
			loadData("sms/getCode","",function(data){
				console.log(data);
				if(data.code=="c1"){
					console.log("获取成功");
					if(id=='#getNewCode'){
						gvpsmsNew = true;
					}else{
						gvpsmsOld = true;
					}
				}else{
					if(id=='#getNewCode'){
						gvpsmsNew = false;
					}else{
						gvpsmsOld =false;
					}
					alert("系统故障");
				}
			},"json","get");
		}
		
		
		/*校验获取的验证码是否正确*/
		var vpsmsNew = false;
		var vpsmsOld = false;
		function checkPhoneSms(id){
			if(id=="#oldPhoneCode"&&!gvpsmsOld){return false;}
			if(id=="#newPhoneCode"&&!gvpsmsNew){return false;}
			loadData("sms/checkCode",$(id).serialize(),function(data){
				console.log(data);
				if(data.code=="c1"){//成功
					$(id).parent("div").next("div").children("span").html("<font color='green'>恭喜，填写正确！</font>")
					if(mode=="change"){
						$(".new").show();
						$(".old").hide();
					}
					if(id=="#oldPhoneCode"){
						vpsmsOld = true;
					}
					if(id=="#newPhoneCode"){
						vpsmsNew = true;
					}
				}else{
					$("#phoneCode").parent("div").next("div").children("span").html("<font color='red'>短信验证码填写错误！</font>")
					if(id=="#oldPhoneCode"){
						vpsmsOld = false;
					}
					if(id=="#newPhoneCode"){
						vpsmsNew = false;
					}
				}
			},"json","post");
		}

	</script>