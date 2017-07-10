<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %>
<link href="static/css/mLogin.css" charset="utf8" type="text/css" rel="stylesheet">
<script src="static/js/login.js" charset="utf8" type="text/javascript" ></script>
<title>登录</title>
</head>
<body>
<section class="bigbox">
<div id="login">
	<div >
		<form id="login-form">
				<input type="hidden" id="loginMethod" name="loginMethod" value="uname"/>
				<input type="hidden" id="haveImgCode" name="haveImgCode" value="false"/>
			  	<!-- 账号 -->
			    <div class="uname item">
			      <label>用户名</label>
			      <input type="text" id="uname" name="uname" class="form-control"  placeholder="<s:message code="login.unameTip"/>">
			      <span class="input-tip"></span>
			   	</div>
			   	<label>&nbsp;</label>
			   	<div id="pwdRand" hidden></div>
			  	<!-- 密码 -->
			    <div class="pwd item">
			      <label>密码</label>
			      <input id="pwd" type="password" name="password" class="form-control"  placeholder="<s:message code="login.pwdTip"/>">
			      <span class="input-tip"></span>
			    </div>
			    <label>&nbsp;</label>
			   	<!--验证码-->
			    <div class="form_imgCode item">
			    	 <label>图形验证码</label>
			       <input type="text" name="authCode" class="form-control valideInput"  
			          placeholder="<s:message code='login.valideCodeTip'/>" maxlength="6">
			       <img src="<%=basePath%>imgvalid/getImgCode" class="img-code" title="换一换" id="imgAuthCode"
					        onclick="this.src='<%=basePath%>'+'imgvalid/getImgCode?time='+new Date().getTime()"
					        ver_colorofnoisepoint="#888888"/>
			   		<span class="input-tip"></span>
			    </div>
			   
			  	<!-- 提交 -->
			    <div class="form_submit contentM20">
			      <input type="button" id="msubmit" class="btn btn btn-primary btn-block" value='<s:message code="login.submit"/>' />
			    </div>
			  
			  
			</form>
	</div>
</div>
</section>
</body>
<script type="text/javascript">
$("#msubmit").click(function(){
	login();
});
function login(){
	var rand = $("#pwdRand").text();
	oldpwd = $("#pwd").val();
	var pwdRs = new Array();
	pwdRs = xor(oldpwd,rand);
	newpwd = Bytes2Str(pwdRs);
	$("#pwd").val(newpwd);
	loadData("manager/login",$("#login-form").serialize(),function(data){
		if(data.code=='c1'){
			//alert("success");
			window.location.href="manager/panel";
		}else{
			window.wxc.xcConfirm("拒绝访问","error");
		}
	},"json","post");
}
</script>
