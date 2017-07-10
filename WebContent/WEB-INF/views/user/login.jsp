<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %> 
<link href="static/css/login.css" type="text/css" rel="stylesheet">
<script src="static/js/login.js" charset="utf8" type="text/javascript" ></script>
<title><s:message code="login.tile"/></title>
</head>
<body>
<section class="bigbox">
	<div class="login" >
	<div class="login_top" >
		<a href="index">
			<img  class="logoimg"  src="static/img/common/logo.png" alt="必易为商城" />
		</a>
		<div class="welcome"><s:message code="login.welcome"/></div>
	</div>
	<div class="login_bottom" >
		<div class="login_main">
			<h3><s:message code="login.tile"/></h3>
			<hr/>
			<form id="login-form">
				<input type="hidden" id="loginMethod" name="loginMethod" value="uname"/>
				<input type="hidden" id="haveImgCode" name="haveImgCode" value="false"/>
			  	<!-- 账号 -->
			    <div class="form_uname">
			      <div class="form-control glyphicon glyphicon-user" aria-hidden="true"></div>
			      <input type="text" id="account" name="account" class="form-control"  placeholder="<s:message code="login.unameTip"/>">
			      <span class="input-tip"></span>
			   	</div>
			   	<div id="pwdRand" hidden></div>
			  	<!-- 密码 -->
			    <div class="form_pwd">
			      <div class="form-control glyphicon glyphicon-lock" aria-hidden="true"></div>
			      <input id="form-pwd" type="password" name="password" class="form-control"  placeholder="<s:message code="login.pwdTip"/>">
			      <span class="input-tip"></span>
			    </div>
			    
			   	<!--验证码-->
			    <div class="form_imgCode" hidden>
			       <input type="text" name="authCode" class="form-control valideInput"  
			          placeholder="<s:message code='login.valideCodeTip'/>" maxlength="6">
			       <img src="<%=basePath%>imgvalid/getImgCode" class="img-code" title="换一换" id="imgAuthCode"
					        onclick="this.src='<%=basePath%>'+'imgvalid/getImgCode?time='+new Date().getTime()"
					        ver_colorofnoisepoint="#888888"/>
			   		<span class="input-tip"></span>
			    </div>
			    <!--忘记密码-->
			    <div class="textR">
			    	<a href="lostPwd"><s:message code="login.lostPwd"/></a>
			    </div>
			  	<!-- 提交 -->
			    <div class="form_submit contentM20">
			      <input type="button" id="submit" class="btn btn btn-primary btn-block" value='<s:message code="login.submit"/>' />
			    </div>
			    <!-- 提示注册 -->
			    <div class="textR">
			      <a href="regist"><s:message code="login.registTip"/></a>
			    </div>
			  
			</form>
		</div>
	</div>
	</div>
</section>
<%@include file="../footer.jsp" %>
