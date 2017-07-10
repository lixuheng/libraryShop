<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<link href="static/css/index.css" charset="utf8" type="text/css" rel="stylesheet">
<script src="static/js/index.js" charset="utf8" type="text/javascript" ></script>
<title>必易为商城</title>
</head>
<body>
<%@include file="nav.jsp" %>
<section id="index" class="bigbox">
<div class="indexcontent">
<div class="main">
	<div class="l_main"> 
		<div class="l_top_main">
			<a href="index">
				<img style="width:180px;margin:4px 0px 0px 4px;" src="static/img/common/logo.jpg" class="img-responsive" alt="logo"> 
			</a>
		</div>
		<div class="l_down_main">
			<br/>
			<div class="font20">图书分类</div>
			<ul id="navUl">
			<!-- style="position:relative" -->
				<li class="categoryClass" v-for="(item,index) in category" :id="'categoryid'+index" @mouseleave="showMain()">
					<div style="line-height:30px;width:100px;height:30px;"  @mouseenter="showCategoryBox(index)"  @mouseleave="quback(index)" href="#">
						<h4>{{item.name}}</h4>
						<div  v-if="index==active" style="padding:20px;z-index:1000px;position:absolute;top:0px;left:140px;height:475px;width:790px;background:#ddd">
							<div  v-for="fitem in item.fitems">
								<h4>
									<hr/>
									<a href="#">
										{{fitem.name}}
									</a>
								</h4>
									<span v-for="sitem in fitem.sitems"  @click="showMain()">
										<a :href="getCategoryUrl(item.type,fitem.type,sitem.type)">{{sitem.name}}</a>
										&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div><!-- l_down_main -->
	</div><!-- l_main -->
	
	
	<div id="mainShow" class="m_main">
		<div class="m_top_main">
			<div class="">
				<ul class="nav nav-pills titlebtn">
				  <li role="presentation"><a href="#">秒杀专场</a></li>
				  <li role="presentation"><a href="#">领优惠券</a></li>
				  <li role="presentation"><a href="#">满减专场</a></li>
				  <li role="presentation"><a href="#">热销榜</a></li>
				  <li role="presentation"><a href="#">人气榜</a></li>
				  <li role="presentation"><a href="#">关注榜</a></li>
				</ul>
			</div>
		</div><!-- m_top_main -->
		
		<div class="m_down_main" >
			<div class="contentM20">
				<div class="mm_main">
					<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					  <!-- Indicators -->
					  <ol class="carousel-indicators">
					    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
					    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
					    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
					  </ol>
					
					  <!-- Wrapper for slides -->
					  <div class="carousel-inner" role="listbox">
					    <div class="item active">
					      <img src="static/img/index/58f0928fN4254b4a7.jpg" alt="...">
					      <div class="carousel-caption">
					        ...
					      </div>
					    </div>
					    <div class="item">
					      <img src="static/img/index/58f7431eN83bfdb31.jpg" alt="...">
					      <div class="carousel-caption">
					        ...
					      </div>
					    </div>
					    
					    <div class="item">
					      <img src="static/img/index/58f75c5cNfc700b9f.jpg" alt="...">
					      <div class="carousel-caption">
					        ...
					      </div>
					    </div>
					  </div>
					
					  <!-- Controls -->
					  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
					    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					    <span class="sr-only">Previous</span>
					  </a>
					  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
					    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					    <span class="sr-only">Next</span>
					  </a>
					</div>
				</div><!--mm_main -->
			</div><!-- contentM20 -->
			<div class="mm_subl_main">
				<img src="static/img/index/renmin.jpg" alt="文学" />
			</div><!-- mm_subl_main -->
			<div class="mm_subr_main">
				<img src="static/img/index/boot.jpg" alt="文学" />
			</div><!-- mm_subr_main -->
		</div><!-- m_down_main -->
	</div><!-- m_main -->
	<div class="r_main">
		<div class="r_top_main">
			<div class="shopingcar">
				<img src="static/img/common/shopingcar.png" alt="购物车"/>
				<!-- <a href="trade/shoppingCar" >我的购物车</a> -->
				<a href="trade/shoppingCar" >我的购物车</a>
			</div>
			<div class="shopingcar">
				<img src="static/img/common/care.png" alt="购物车"/>
				<a href="" >我的关注</a>
			</div>
		</div><!--r_top_main  -->
		<div class="r_down_main">
			<div class="userinfo">
				<img alt="my" src="">
				<div class="text">
					hi,欢迎来到必易为<br/><br/>
					<a href="login">登录</a>
					<a href="regist">注册</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</div> 
			<div class="title">我的借阅</div>
			<div class="myborrow">
				<table class="table">
					<tr><th>当前借阅</th><th colspan="2">操作</th></tr>
					<tr><td colspan="2">哈1哈哈1哈哈1</td><td>归还</td><td>续借</td></tr>
					<tr><td colspan="2">哈哈2</td><td>归还</td><td>续借</td></tr>
					<tr><td colspan="2">哈哈3</td><td>归还</td><td>续借</td></tr>
					<tr><td colspan="2">哈哈4</td><td>归还</td><td>续借</td></tr>
					
				</table>
			</div>
		</div><!-- r_down_main -->
	</div>
</div><!-- main -->
<div class="extends">
<!--  
	<div class="ext_F4">
		<div class="ext_F4_l">
		<table class="table table-bordered">
			<tbody>
				<tr><td>&nbsp;w</td>
					<td>&nbsp;w</td>
				</tr>
				<tr><td>&nbsp;ii</td>
					<td>&nbsp;ii</td>
				</tr>
			</tbody>
		</table>
		</div>
		<div class="ext_F4_r">
		<table class="table table-bordered">
			<tbody>
				<tr><td>&nbsp;w</td>
					<td>&nbsp;w</td>
				</tr>
				<tr><td>&nbsp;ii</td>
					<td>&nbsp;ii</td>
				</tr>
			</tbody>
		</table>
		</div>
	</div>
	-->
	<!-- ext_F4 -->
	
	<ul id="bookClass" class="nav nav-tabs">
  		<li role="presentation" v-for="colume in columes">
  			<a href="javascript:">{{colume.name}}</a>
  		</li>
	</ul>
	
	
	<div class="ext_F6">
		<table class="table table-bordered">
			<tbody>
				
				<tr>
					<c:forEach items="${goodslist }" var="goods" varStatus="cur" end="5">
						<td>
							<div class="ext_F6_E">
								<div class="up">
									<a href="goods/id/${goods.goodsId }" alt="${goods.goodsName }">
										<img src="file/display?name=${goods.book.folder }"/>
									</a>
								</div>
								<div class="mid">
									<a href="goods/id/${goods.goodsId }" alt="${goods.goodsName }">
										${goods.aWordIntro}
									</a>
								</div>
								<div class="down">
									<span class="price">￥<fmt:formatNumber type="number" value="${goods.price}" pattern="0.00"/></span>&nbsp;
									<span class="oPrice">￥<fmt:formatNumber type="number" value="${goods.originalPrice }" pattern="0.00"/> </span>
								</div>
							</div>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${goodslist }" var="goods" varStatus="cur" begin="6" end="11">
						<td>
							<div class="ext_F6_E">
								<div class="up">
									<a href="goods/id/${goods.goodsId }" alt="${goods.goodsName }">
										<img src="file/display?name=${goods.book.folder }"/>
									</a>
								</div>
								<div class="mid">
									<a href="goods/id/${goods.goodsId }" alt="${goods.goodsName }">
										${goods.aWordIntro}
									</a>
								</div>
								<div class="down">
									<span class="price">￥<fmt:formatNumber type="number" value="${goods.price}" pattern="0.00"/></span>&nbsp;
									<span class="oPrice">￥<fmt:formatNumber type="number" value="${goods.originalPrice }" pattern="0.00"/> </span>
								</div>
							</div>
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div><!-- ext_F6 -->
</div><!-- extends -->
</div><!-- indexcontent -->
</section><!-- bigbox -->
<%@include file="footer.jsp" %>










<!-- test
<button id="test" class="btn-success">测试按钮</button>
<script>
$("#h").css("width","300px").css("height","300px").css("background-color","red");
$("#h").text("hah");
$("#test").click(function(evt){
	$.ajax({
		url:"hasUserUname",
		type:"get",
		async:true,
		cache:false,
		success:function(evt){
			console.log("success");
			//window.document.location.href="/test";
			alert(evt);
		},
		error:function(evt){
			console.log("error");
		}
	}
	)
})
</script>
 -->