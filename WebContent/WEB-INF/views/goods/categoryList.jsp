<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %>
<link href="static/css/searchlist.css" charset="utf8" type="text/css" rel="stylesheet">
<title>搜索商品</title>
</head>
<body>
<%@include file="../nav.jsp" %>
<section class="bigbox">
	<div id="searchResult">
		<div class="title" >
			<div class="column goods">商品</div><!-- 图片和 简介-->
			<div class="column price">价格</div>
			<div class="column instock">库存</div>
			<div class="column copy">副本量</div>
			<div class="column opt">操作</div><!-- 加入购物车，借阅，关注 ，下载 -->
		</div>
		
		<c:forEach items="${goodsList}" varStatus="i" var="goods" >
		<div class="main" >
			<div class="img">
				<a href="goods/id/${goods.goodsId}" alt="${goods.goodsName }">
					<img alt="图片" src="file/display?name=${goods.entryFolder }">
				</a>
			</div>
			<div class="column intro">
				<a href="goods/id/${goods.goodsId}" alt="${goods.goodsName }">
					${goods.aWordIntro}
				</a>
			</div>
			<div class="column price">￥<span>${goods.price}</span></div>
			<div class="column instock"><span>${goods.inStock}&nbsp;件</span></div>
			<div class="column copy"><span>${goods.copyForBorrow}&nbsp;件</span></div>
			<div class="column opt"><a href="javascript:;">加入购物车</a></div>
			<div class="column opt"><a href="javascript:;">借阅</a></div>
			<div class="column opt"><a href="javascript:;">关注</a></div>
			<div class="column opt"><a href="javascript:;">下载</a></div>
		</div>
		</c:forEach>
	
	<c:if test="${empty goodsList}">
		<h3>空空如也！</h3>
	</c:if>
	</div>
</section>
<%@include file="../footer.jsp" %>
