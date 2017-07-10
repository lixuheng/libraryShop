<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %>
<link href="static/css/shoppingCar.css" charset="utf8" type="text/css" rel="stylesheet">
<script src="static/js/shoppingCar.js" charset="utf8" type="text/javascript" ></script>

<title>我的购物车</title>
</head>
<body>
<%@include file="../nav.jsp" %>
<section class="bigbox">
	<div id="cart">
		<div class="search">
			<a href="index">
				<img style="width:180px;margin:4px 0px 0px 4px;" src="static/img/common/logo.jpg" class="img-responsive" alt="logo"> 
			</a>
			<form id="search">
				<input type="text" size="40" name="keywords" id="sbtn"/>
				<input type="button" class="btn btn-info" value="搜索"/>
			</form>
		</div>
		<div class="car_title">
			<div class="column t-checkbox">
				<div class="t-input-check">
					<input @click="selectAll()" class="allSelect" name="allSelect" type="checkbox"  />
					<label>全选</label>
				</div>
			</div>
			<div class="column t-goods">商品</div>
			<div class="column t-props">&nbsp;</div>
			<div class="column t-price">单价</div>
			<div class="column t-quantity">数量</div>
			<div class="column t-subSum">小计</div>
			<div class="column t-action">操作</div>
		</div><!-- car_title -->
		
		
		<div id="vueData" class="car_item_list">
			
			<div class="item" v-for="(item, index) in cartForm">
				<div class="idclass" :id="getId(index)" hidden>{{index}}</div>
				<div class="column checkbox">
					<div class="input-check">
						<input class="oneCheckBox" @click="select(index)"  type="checkbox" /><!-- :checked="doall" -->
					</div>
				</div>
				<div class="column goods">
					<img  :src="url(item.folder)" :alt="item.folder" />
					<div class="aWordIntro" v-cloak>{{item.orderItem.goodsName}}</div>
				</div>
				<div class="column price" v-cloak>￥{{convert(item.orderItem.onePrice)}}</div>
				<div class="column quantity"><!--require -->
					<form id="form" class="chCount">
						<input name="orderItemId" type="hidden" :value="item.orderItem.orderItemId"/>
						<input name="goodsId" type="hidden" :value="item.orderItem.goodsId"/>
						<input @click="item.orderItem.count=sub(item.orderItem.count)" class="sub" type="button" value="-"/>
						<input @mouseout="item.orderItem.count=change(item.orderItem.count)"  @focusout="item.count=change(item.orderItem.count)" 
						name="count" class="text" type="text" size="3" v-model="item.orderItem.count"/>
						<input @click="item.orderItem.count=add(item.orderItem.count)" class="add" type="button" value="+"/>
					</form>
				</div>
				<div class="column subSum" v-cloak>
					￥{{calculate(item.orderItem.count,item.orderItem.onePrice)}}
				</div>
				<div class="column action">
					&nbsp;<button @click="remove(index)" class="btn btn-info" href="javascript:;">移除</button>
				</div>
			</div>
			
		</div><!-- car_item_list  copy right by 孟盛能 2017-->
		<div class="settlement">
			<div class="bottom-checkbox">
				<input @click="selectAll()" class="allSelect" name="allSelect" type="checkbox"  />
				<label>全选</label>
			</div>
			
			<div class="tipCount" v-cloak> 当前选中:{{selectCount}}件物品 </div>
			<div class="tipPrice"> 总计:<span v-cloak>￥{{totalPrice}} </span> </div>
			<div class="tipSave"> 已节省:<span v-cloak>￥{{haveSave}} </span> </div>
			
			<div class="doSettlement">
				<button @click="settlement()" class="btn btn-warning btn-lg">结算</button>
			</div>
			
		</div>
	</div><!--#cart  -->
</section>


		
<%@include file="../footer.jsp" %>
