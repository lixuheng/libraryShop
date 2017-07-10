<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %>
<link href="static/css/controlPanel.css" charset="utf8" type="text/css" rel="stylesheet">
<script src="static/js/controlPanel.js" charset="utf8" type="text/javascript" ></script>
<title>控制面板</title>
</head>
<body>
<section class="bigbox">
	<div class="head contentM10" >
			<label>管理员:</label>
			<span>
				${sessionScope.MANAGER.uname }
			</span>
			<span>
				<a href="manager/exit">安全退出</a>
			</span>
			<button class="active btn btn-info">用户行为</button>
			<button class="btn btn-info">异常分析</button>
			<button class="btn btn-info">系统设置</button>
	</div>
	
	<div class="main contentM10">
		<div id="behavior" class="switch">
			<div class="subHead">
				<button class="btn btn-info active">在线用户</button>
				<button class="btn btn-info ">历史用户</button>
			</div>
			
			<div id="online" >
				<div class="title">
					<div class="column">用户名</div>
					<div class="column">邮箱</div>
					<div class="column">手机</div>
					<div class="column">操作</div>
				</div>
				<div v-for="(user,index) in onlineUsers" class="item">
					<div class="column">{{user.uname}}</div>
					<div class="column">{{user.email}}</div>
					<div class="column">{{user.phone}}</div>
					<div class="column">
						<form id="detail-form">
							<input type="hidden" name="userId" :value="user.userId" />
							<input @click="detail()" type="button" value="详情" />
						</form>
					</div>
					<div class="column">
						<form  id="chat-form">
							<input type="hidden" name="userId" :value="user.userId" />
							<input @click="chat()" type="button" value="折线图" />
						</form>
					</div>
				</div>
			</div>
			
			
			<div id="offline" hidden>
				<div class="title">
					<div class="column">用户名</div>
					<div class="column">邮箱</div>
					<div class="column">手机</div>
					<div class="column">操作</div>
				</div>
				<div class="item">
					<div class="column"> </div>
					<div class="column">邮箱</div>
					<div class="column">手机</div>
					<div class="column">操作</div>
				</div>
			</div>
			
			
		</div>
		
		
		
		
	</div>
</section>
</body>