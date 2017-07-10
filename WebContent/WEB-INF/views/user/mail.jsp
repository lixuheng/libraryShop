<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../head.jsp"%>
<title>邮件验证</title>
</head>
<body>
	<section class="bigbox">
		<div hidden id="mode">${mode }</div>
		<div id="emailBox" >
			<div id="head" class="contentM10">
				<h4 id="title">更换邮件</h4>
			</div>
			<div id="main" class="contentM20">
				<form id="emailForm">
					<div class="old">
						<label>已验证的邮件:</label>
						<input name="email" id="oldEmail" placeholder="已验证的邮件" class="form-control"/>
					</div>
					<div class="input-tip old">
					    <span></span>
					</div>
					<div >&nbsp;</div>
					<div class="new">
						<label>新的邮件:</label>
						<input name="email" id="newEmail" placeholder="新的邮件" class="form-control"/>
					</div>
					<div class="input-tip new">
					    <span></span>
					</div>
					<div >&nbsp;</div>
					<div class="form-item">
						<div class="getcode">
							<button  id="getOldCode" class="btn btn-default old" type="button">点击为当前绑定邮件获取验证码</button>
							<button  id="getNewCode" class="btn btn-default new" type="button">点击为新邮件获取验证码</button>
							<br/>
						</div>
						<div>&nbsp;</div>
						<div class="old">
							<label>请输入当前绑定邮件验证码:</label>
							<input type="text" name="code" 
								id="oldEmailCode" class="form-control" placeholder="请输入当前绑定邮件验证码"
								autocomplete="off" />
							<i class="i-status"></i>
						</div>
						<div class="input-tip old">
					    	<span></span>
						</div>
						<div>&nbsp;</div>
						<div  class="new">
							<label>请输入新邮件验证码:</label>
							<input type="text" name="code" 
								id="newEmailCode" class="form-control" placeholder="请输入新邮件验证码"
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
<style type="text/css">
.bigbox{
	width:600px;
	margin:0px auto;
	margin-top:60px;
}
#emailBox{
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
			$("#title").text("更换邮件");
		}else if(mode=="checkNew"){
			$("#title").text("新增邮件验证");
			$(".old").hide();
		}else if(mode=="checkOld"){
			$("#title").text("验证邮件");
			$(".new").hide();
		}else{
			alert("发生错误，请关闭本页面");
		}
		
		/*校验事件*/
		$("#oldEmail").focusout(function(){
			checkEmail("#oldEmail");
		})
		$("#oldEmail").mouseout(function(){
			checkEmail("#oldEmail");
		})
		$("#newEmail").focusout(function(){
			checkEmail("#newEmail");
		})
		$("#newEmail").mouseout(function(){
			checkEmail("#newEmail");
		})
		
		/*获取 邮件验证事件*/
		$("#getOldCode").click(function(){
			if(oldB){
				getEmailCode("#getOldCode");
			}else{
				checkEmail("#oldEmail");
			}
			
		})
		
		$("#getNewCode").click(function(){
			if(newB){
				getEmailCode("#getNewCode");
			}else{
				checkEmail("#newEmail");
			}
		})
		
		
	
		/*确认*/
		$("#ensure").click(function(){
			var isok = null;
			if(mode=="change"){
				isok = gvpsmsOld&&gvpsmsNew;
			}else if(mode=="checkNew"){
				isok=gvpsmsNew;
			}else if(mode=="checkOld"){
				isok=gvpsmsOld;
			}
			var param = 'mode='+mode+"&oldEmail="+$("#oldEmail").val()+"&oldCode="+$("#oldEmailCode").val()+"&newEmail="+$("#newEmail").val()+"&newCode="+$("#newEmailCode").val();
			if(isok){
				loadData("user/mail",param,function(data){
					console.info(data);
					if(data.code == "c1"){
						window.wxc.xcConfirm("已成功操作,请关闭本页面","success");
					}else{
						window.wxc.xcConfirm("失败","error");
					}
				},"json","post")
			}else{
				window.wxc.xcConfirm("邮件验证不正确","error");
			}
			
		})
		
		
		/*检查邮件的正确性，传入参数是邮件input的id，附带两个标识*/
		var oldB =false;
		var newB =false;
		function checkEmail(id){
			len = $(id).val().length;
			//console.log("len="+len)
			if(len==0) return ;
			if($(id).val().length==0) return;
			reg=/^[A-Za-z0-9_\-]+@[A-Za-z0-9_\-]+\.[A-Za-z0-9_\-]+$/;
			if(!reg.test( $(id).val()) ){
				$(id).parent("div").next("div").children("span").html("<font color='red'>邮箱格式不对！</font>");
				ve = false;
				return ;
			}
			else{
				$(id).parent("div").next("div").children("span").html("");
				url = "checkEmail";				
				if(id=="#oldEmail"){
					url = "user/checkEmail";
				}
				//检查邮件是否已经存在
				loadData(url,$(id).serialize(),function(data){
					console.log(data);
					if(data.code=="c1"){//邮件已经存在
						if(id=='#newEmail'){
							newB = false;
							$(id).parent("div").next("div").children("span").html("<font color='red'>该邮件已经被注册！</font>")
						}
						if(id=='#oldEmail'){
							oldB =true;
							$(id).parent("div").next("div").children("span").html("")
						}
					}else{//邮件不存在
						if(id=='#newEmail'){
							newB = true;
							$(id).parent("div").next("div").children("span").html("")
						}
						if(id=='#oldEmail'){
							oldB = false;
							$(id).parent("div").next("div").children("span").html("<font color='red'>该邮件不存在，无法验证！</font>")
						}
						
					}
				},"json","get");
		    }
		}
		
		//获取邮件验证码，附带两成功与否的标志
		var gvpsmsOld = false;
		var gvpsmsNew = false;
		function getEmailCode(id){
			$(id).attr('disabled',true);
			$(id).css("backgroud-color","gray");
			var time = 300;
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
			var param = null;
			if(id=="#getOldCode"){
				param ="recipient="+$("#oldEmail").val();
			}else{
				param ="recipient="+$("#newEmail").val();
			}
			loadData("user/distributeCode",param+"&mode="+mode,function(data){
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
		
</script>
</body>