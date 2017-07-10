<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %>
<link href="static/css/regist.css" type="text/css" rel="stylesheet">
<script src="static/js/regist.js" charset="utf8" type="text/javascript" ></script>
<title><s:message code="regist.title"/></title>
</head>
<body>
<section class="bigbox">
	<div class="regist" >
		<div class="regist_top" >
			<a href="index">
				<img class="logoimg" src="static/img/common/logo.png" alt="必易为商城" />
			</a>
			<div class="welcome"><s:message code="regist.welcome"/></div>
			<div class="top_login">
				<span class="red">已有账号？</span>
				<a href="login" >立即登录</a>
			</div>
		</div>
		<div class="regist_bottom" >
			<div class="regist_main">
					<!-- <br/><br/> -->
					<form id="regist-form">
						<input type="hidden" id="method" name="method" value="phone"/>
						<!-- 用户名 -->
					    <div class="form-item form-item-account" id="form-item-account">
					        <label>用　户　名</label> 
					        <input type="text" id="form-account" name="uname" class="form-control" autocomplete="off" maxlength="20"
					        placeholder="您的账户名和登录名" default='<i class="i-def"></i>支持中文、字母、数字、“-”“_”的组合，4-20个字符' />
					        <i class="i-status"></i>
					    </div>
					    <div class="input-tip">
					        <span></span>
					    </div>
					    <!-- 密码  -->
					    <div id="pwdRand" hidden>${pwdRand}</div>
					    <div class="form-item">
					        <label>设 置 密 码</label>
					        <input style="display:none" type="password" name="pwd" class="fakeinput"/>
					        <input type="password" name="password" id="form-pwd" class="form-control" maxlength="20"
					        placeholder="建议至少使用两种字符组合" default="<i class=i-def></i>建议使用字母、数字和符号两种及以上的组合，6-20个字符" />
					        <i class="i-status"></i>
					    </div>
					    <div class="input-tip">
					        <span></span>
					    </div>
					    <!-- 确认密码   -->
					    <div class="form-item">
					        <label>确 认 密 码</label>
					        <input style="display:none" type="password" name="pwdRepeat" class="fakeinput"/>
					        <input type="password" name="pwdRepeat" id="form-equalTopwd" class="form-control" placeholder="请再次输入密码"
					        maxlength="20" default='<i class="i-def"></i>请再次输入密码' />
					        <i class="i-status"></i>
					    </div>
					    <div class="input-tip">
					        <span></span>
					    </div>
					    
					    <!-- 邮箱验证 -->
					    <div class="item-email-wrap" hidden>
					        <div class="form-item">
					            <label>邮 箱 验 证</label>
					            <input type="email" name="email" id="form-email" class="form-control" autocomplete="off"
					            placeholder="建议使用常用邮箱" id="email-input"
					            default='<i class="i-def"></i>完成验证后，你可以用该邮箱登录和找回密码' />
					            <i class="i-status"></i>
					        </div>
					        <div class="input-tip">
					            <span></span>
					        </div>
					        <div class="orPhone"><a href="javascript:;">手机验证</a></div>
					    </div>
					    
					    <!-- 手机验证   -->
					    <div class="item-phone-wrap" >
					        <div class="form-item form-item-phone">
					            <label class="select-country" id="select-country" country_id="0086">中国 0086<a href="javascript:void(0) " tabindex="-1" class="arrow"></a></label>
					            <input type="text" id="form-phone" name="phone" class="form-control" placeholder="建议使用常用手机"
					            autocomplete="off" maxlength="11" default='<i class="i-def"></i>完成验证后，你可以用该手机登录和找回密码' />
					            <i class="i-status"></i>
					        </div>
					        <div class="input-tip">
					            <span></span>
					        </div>
					        <div class="orEmail">
					        	<a href="javascript:;" tabindex="-1">邮箱验证</a>
					        </div>
					    </div>
					    <!-- 图片验证码   -->
					    <div class="form_valideCode">
					        <label>验　证　码</label>
					        <input type="text" autocomplete="off" name="authCode" id="authCode" maxlength="6" class="form-control"
					        placeholder="请输入验证码" default='<i class="i-def"></i>看不清？点击图片更换验证码' />
					        <img src="<%=basePath%>imgvalid/getImgCode" class="img-code" title="换一换" id="imgAuthCode"
					        onclick="this.src='<%=basePath%>'+'imgvalid/getImgCode?time='+new Date().getTime()"
					        ver_colorofnoisepoint="#888888"/>
					    </div>
					    <div class="input-tip">
					        <span></span>
					    </div>
					    <!-- 手机验证码  -->
					    <div class="form-item form-item-phonecode">
					        <label>手机验证码</label>
					        <input type="text" name="code" maxlength="6" id="phoneCode" class="form-control"
					        placeholder="请输入手机验证码" autocomplete="off"/>
					        <button id="getPhoneCode" class="btn btn-default btn-sm" type="button">获取验证码</button>
					        <i class="i-status"></i>
					    </div>
					    <div class="input-tip">
					        <span></span>
					    </div>
					    <!-- 同意协议书   -->
					    <div class="form-agreen">
					        <div><input type="checkbox" name="agreen"/>阅读并同意
					        <a href="javascript:;" id="protocol">《必易为用户注册协议》</a>
					        <a href="javascript:;" class="blue" id="privacyProtocolTrigger">《隐私政策》</a>
					    </div>
					    <div class="input-tip">
					        <span></span>
					    </div>
					    
					</div>
					<div>
					    <input type="button" id="submit"  class="contentM10 btn btn-block btn-info" value="立即注册" />
					</div>
				</form>
			</div>
			<div class="regist_right">
				<div class="marginL20">
				<span><img src="static/img/regist/company.png"></span>
				<div class="content10 font20" ><a href="companyRegist">企业注册</a></div>
				</div>
			</div>
	</div>
	</div>
</section>
<%@include file="../footer.jsp" %>