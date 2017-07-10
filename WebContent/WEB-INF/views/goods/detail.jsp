<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<link href="static/css/goodsDetail.css" charset="utf8" type="text/css" rel="stylesheet">
<script src="static/js/goodsDetail.js" charset="utf8" type="text/javascript" ></script>
<title>商品详情</title>
</head>
<body>
<%@include file="../nav.jsp" %>
<section id="goodsDetal" class="bigbox">

<a id="top" class="target">
	<span class="placeholder"></span>
</a>
	<div class="top">
		<div class="logo">
			<a href="index">
				<img style="width:180px;margin:4px 0px 0px 4px;" src="static/img/common/logo.jpg" class="img-responsive" alt="logo"> 
			</a>
		</div>
		
		<div class="title_top">
			<ul class="nav nav-pills titlebtn"><!--0 class="nav nav-pills titlebtn"  -->
				  <li role="presentation"><a href="#">秒杀专场</a></li>
				  <li role="presentation"><a href="#">领优惠券</a></li>
				  <li role="presentation"><a href="#">满减专场</a></li>
				  <li role="presentation"><a href="#">热销榜</a></li>
				  <li role="presentation"><a href="#">人气榜</a></li>
				  <li role="presentation"><a href="#">关注榜</a></li>
			</ul>	
		</div><!-- title_top -->
		
		<div class="shopping">
			<span class="car">
				<a href="trade/shoppingCar">
					<img alt="" src="">购物车
				</a>
			</span>
			<span class="care">
				<a href="trade/mycare"><img alt="" src="">关注</a>
			</span>
		</div>
		
	</div><!-- top -->
	
	
	<div class="lookPosition">
	<span class="big"><a href="">图书</a></span>&nbsp;&gt;&nbsp;
	<span><a href="" >文学</a>&nbsp;&gt;&nbsp;<a href="">小说</a></span></div>
	
			
	<div class="selectGoods">
		<div class="select_left">
			<img class="goodsImg" src="file/display?size=b&name=${goods.book.folder }"/>
			<div class="share">
				<a href="javascript:;">
					分享
				</a>
			</div>
		</div><!-- select_left -->
		
		<div class="select_mid">
			<div class="goods_title_bar">
				<div class="name">${goods.goodsName }</div>
				<div class="aWordIntro">${goods.aWordIntro } </div>
				<div class="author">
					<%-- <%
						String author =  request.getParameter("");
						String authors[] 
					%> --%>
					<a href="goods/search/author/${goods.book.author }">
						${goods.book.author }
					</a>
				</div>
			</div>
			
			
			<div class="price_bar">
				<div class="price_div">必易为价：
					<span class="price">
						￥<fmt:formatNumber type="number" value="${goods.price}" pattern="0.00"/>
					</span>&nbsp;
					<span class="oPrice">
						[定价&nbsp;:&nbsp;
						<span class="o">
						￥<fmt:formatNumber type="number" value="${goods.originalPrice }" pattern="0.00"/> 
						</span>
						]
					</span>
				</div>
				<div class="promotion">促销信息：<span>满300送20</span></div>
				<div class="coupon">优&nbsp;&nbsp;惠&nbsp;&nbsp;券：
					<a href="getcoupon">领取优惠券</a></div>
				<div class="ranking">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：
					自营排名第<span>12</span>位</div>
			</div>
			
			<div class="join_bar">
				<div class="quantity">
					<%-- <div hidden>
						<form id="car_hidden_form">
							<input name="goodsId" value=${goods.goodsId }/>
							<input name="goodsName" value=${goods.goodsName }/>
						</form>
					</div> --%>
					<form id="quantity_form" class="form-inline">
						<input type="hidden" name="goodsId" value="${goods.goodsId }"/>
						<input type="hidden" name="goodsName" value="${goods.goodsName }"/>
						<input type="hidden" name="shopId" value="${goods.shopId }"/>
						<input type="hidden" name="onePrice" value="${goods.price }"/>
						<input class="input-small" type="text" name="count" v-model="number" @focusout="change" @mouseout="change" value="1" size="4"/>
						<div class="controller">
							<input type="button" @click="add" id="add" value="+"/>
							<input type="button" @click="sub" id="sub" value="-"/>
						</div>
					</form>
				</div>
				
				
					<c:if test="${goods.inStock-goods.copyForBorrow>0 }">
						<button id="joinCar"  class="join btn btn-info" href="javaScript:;" >
							加入购物车
						</button>
					</c:if>
					
					<button id="joinCare" class="care btn btn-info" href="javaScript:;">
						关注
					</button>
					
					<c:if test="${goods.copyForBorrow>0 }">
						<button id="borrow" class="borrow btn btn-success" href="javaScript:;">
							借阅
						</button>
					</c:if>
					
					<c:if test='${goods.book.eBook=="1"}'>
						<form id="down-form" action="file/download">
							<input name="path" type="hidden" value='${goods.entryFolder}'/>
							<!--${goods.goodsName}  -->
							<input name="goodsId" type="hidden" value='${goods.goodsId}'/>
							<input name="name" type="hidden" value='pdf'/>
							<input name="onlyDown" type="hidden" value='true'/>
							<input id="down" class="down btn btn-success" type="button" value="下载" />
						</form>
					</c:if>
			</div><!-- join_bar -->
		
		</div><!--select_mid  -->
		
		<div class="select_right">
			<div id="shop" class="shop">
				商家 {{shop}}
			</div>
		</div>
			
	</div><!-- selectGoods  -->
	
		
		
	<hr class=""/>	
	
	
	
	<div class="intorAndConments">
		
		<div class="recommand_goods">
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
			
		</div><!-- recommand_goods -->
		
		<div id="intro_div" class="intro_div" >
			<div id="gudingTa" class="chooser_bar"> 
				<ul id="choooser-d-c" class="nav nav-tabs">
				  <li role="presentation">
				  	<a href="javascript:" @click="seeIntro">商品详情</a>
				  </li>
				  <li role="presentation">
				  	<a href="javascript:" @click="seeComm">商品评价</a>
				  </li>
				</ul>
			</div> 
			<div v-if="introSeen" class="intro">
				<div class="intro_main">
					<div class="base_intro">
						<table>
							<tr>
								<td>出版社：${goods.book.press}</td>
								<td>ISBN：${goods.book.isbn}</td>
								<td>版次：${goods.book.edition}</td>
								<td>商品编码：${goods.goodsId}</td>
							</tr>
							<tr>
								<td>包装：${goods.book.bookPackage}</td>
								<td>开本：${goods.book.format}</td>
								<td>出版时间： <fmt:formatDate value="${goods.book.pressDay}" dateStyle="long" /></td>
								<td>用纸：${goods.book.pager}</td>
							</tr>
							<tr>
								<td>页数：${goods.book.allPages}</td>
								<td>可外借数：${goods.copyForBorrow}</td>
								<td></td>
								<td></td>
							</tr>
						</table>
					
					</div>
					<div class="detail_intro">
						<div class="features">
						<!-- 专用锚记法  -->
							<a id="features" class="target">
								<span class="placeholder"></span>
							</a>
							<div class="title">
								产品特色
							</div>
							
						</div>
						<div class="recommand">
							<a id="recommand" class="target">
								<span class="placeholder"></span>
							</a>
							<div class="title">
								编者推荐
							</div>
							${book.recommend}
						</div>
						<div class="context">
							<div class="title">
								<a id="context" class="target">
									<span class="placeholder"></span>
								</a>
								内容简介
							</div>
							${book.inroduction}
						</div>
						<div class="content"></div>
							<div class="title">
								<a id="content" class="target">
									<span class="placeholder"></span>
								</a>
								目录
							</div>
							${book.direcotry}
						<div class="preface"></div>
							<div class="title">
								<a id="preface" class="target">
									<span class="placeholder"></span>
								</a>
								序言
							</div>
							${book.authorPreface}
						<img class="img_intro" src="" alt="" />
					</div><!-- detail_intro -->
				</div><!-- intro_main -->
				<div id="gudingT" class="intro_nav">
					<ul class="nav">
						<li><a href="goods/id/${goods.goodsId}#features">产品特色</a></li>
						<li><a href="goods/id/${goods.goodsId}#recommand">编者推荐</a></li>
						<li><a href="goods/id/${goods.goodsId}#context">内容简介</a></li>
						<li><a href="goods/id/${goods.goodsId}#content">目录</a></li>
						<li><a href="goods/id/${goods.goodsId}#preface">序言</a></li>
						<li><a href="goods/id/${goods.goodsId}#top">回到顶部</a></li>
					</ul>
				</div><!-- intro_nav -->
			</div><!-- intro -->
			
			<div id="comment" v-if="commentSeen" class="comment">
				<div class="goodsRating">
					<div id="good-rating">
						<div>
							<span>
								{{grating}}
							</span>%
						</div>
						好评度
					</div>
					<div id="rating-show">
						好评（{{grating}}%）
						<br/>
						中评（{{mrating}}%）
						<br/>
						差评（{{brating}}%）
					</div>
					<div id="progess">
						<div class="progress">
						  	<div class="progress-bar progress-bar-danger" role="progressbar" :aria-valuenow="grating" 
						  	   aria-valuemin="0" aria-valuemax="100" v-bind:style="{width:grating+'%'}">
						  	</div>
						</div>
						<div class="progress">
						  	<div class="progress-bar progress-bar-danger" role="progressbar" :aria-valuenow="mrating" 
						  	   aria-valuemin="0" aria-valuemax="100" v-bind:style="{width:mrating+'%'}">
						  	</div>
						</div>
						<div class="progress">
							<div class="progress-bar progress-bar-danger" role="progressbar" :aria-valuenow="brating" 
							   aria-valuemin="0" aria-valuemax="100" v-bind:style="{width:brating+'%'}">
						  	</div>
						</div>
					</div>
				</div><!-- goodsRating -->
				<div class="chooser_bar">
					<ul id="choooser-g-m-b" class="nav nav-tabs">
					  <li role="presentation">
					  	<a href="javascript:" @click="allComment">全部评价({{rating.good+rating.mid+rating.bad}})</a>
					  </li>
					  <li role="presentation">
					  	<a href="javascript:" @click="goodComment">好评({{rating.good}})</a>
					  </li>
					  <li role="presentation">
					  	<a href="javascript:" @click="midComment">中评({{rating.mid}})</a>
					  </li>
					  <li role="presentation">
					  	<a href="javascript:" @click="badComment">差评({{rating.bad}})</a>
					  </li>
					</ul>
				</div><!--  chooser_bar-->
				<div id="common_switch_box">
					<div class="list" id="alllist" >
						<div class="oneComment">
							<div class="start_div">
								<div class="star5"></div>
								<div class="comment_time">
									{{comment.date}}
								</div>
							</div><!-- start_div -->
							<div class="comment">
								<div class="context">
									{{comment.comment}}
								</div>
								<div class="img_bar">
								</div>
							</div><!-- coment -->
							<div class="user_info">
								<div>{{comment.user.uname}}</div>
								<div>{{comment.user.degree}}</div>
							</div><!-- user_info -->
						</div><!-- oneComment -->
					</div><!-- alllist -->
					<div  class="list" id="goodlist">
						<div class="oneComment">
							<div class="start_div">
								<div class="star5"></div>
								<div class="comment_time">
									{{comment.date}}
								</div>
							</div><!-- start_div -->
							<div class="comment">
								<div class="context">
									{{comment.comment}}
								</div>
								<div class="img_bar">
								</div>
							</div><!-- coment -->
							<div class="user_info">
								<div>{{comment.user.uname}}</div>
								<div>{{comment.user.degree}}</div>
							</div><!-- user_info -->
						</div><!-- oneComment -->
					</div><!-- goodlist -->
					<div  class="list" id="midlist">
						<div class="oneComment">
							<div class="start_div">
								<div class="star5"></div>
								<div class="comment_time">
									{{comment.date}}
								</div>
							</div><!-- start_div -->
							<div class="comment">
								<div class="context">
									{{comment.comment}}
								</div>
								<div class="img_bar">
								</div>
							</div><!-- coment -->
							<div class="user_info">
								<div>{{comment.user.uname}}</div>
								<div>{{comment.user.degree}}</div>
							</div><!-- user_info -->
						</div><!-- oneComment -->
					</div><!-- midlist -->
					<div  class="list" id="badlist">
						<div class="oneComment">
							<div class="start_div">
								<div class="star5"></div>
								<div class="comment_time">
									{{comment.date}}
								</div>
							</div><!-- start_div -->
							<div class="comment">
								<div class="context">
									{{comment.comment}}
								</div>
								<div class="img_bar">
								</div>
							</div><!-- coment -->
							<div class="user_info">
								<div>{{comment.user.uname}}</div>
								<div>{{comment.user.degree}}</div>
							</div><!-- user_info -->
						</div><!-- oneComment -->
					</div><!-- badlist -->
				</div><!-- common_switch_box  -->
			</div><!-- comment -->
		</div><!-- intro_div -->
		
	</div>
	
	
						
</section>
<%@include file="../footer.jsp" %>
