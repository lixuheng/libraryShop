<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../head.jsp"%>
<link href="static/css/settlement.css" charset="utf8" type="text/css" rel="stylesheet">
<script src="static/js/settlement.js" charset="utf8" type="text/javascript"></script>
<title>结算页</title>
</head>
<body>
	<%@include file="../nav.jsp"%>
	<section class="bigbox">
		<div id="title">
			<div class="image">
				<a href="index"> 
				<img style="width:180px; margin: 4px 0px 0px 4px;" 
					src="static/img/common/logo.jpg" 
					class="img-responsive" alt="logo">
				</a>
				<div class="progress"></div>
			</div>
		</div>
		<div id="tip">填写并核对订单信息</div>
		<div id="main">
			<div id="receiveInfo">
				<div class="contentM10">
					<div class="t-new">
						<h3>收货地址</h3>
						<a  data-toggle="modal" data-target="#addrModal">新增收货地址</a>
					</div>
					<div class="contentM10">
						<ul>
							<li v-for="(addr,index) in addrs" v-if="index<initNum" :index="index" :id="addr.addrId" class="oneAddr">
								<div @click="select(getIndexId(index))" 
									id="getIndexId(index)" class="addr_select selectbtn">
									<span :title="addr.addrName">
										{{addr.userName}}
									</span>
									<template  v-if="addr.isDefault">
										<b id='b' ></b>
									</template >
								</div>
								<div class="addr_detail">
									<span class="addr-name" :title="addr.addrName">{{addr.userName}}</span> 
									<span class="addr-info" :title="addr.info">{{addr.info}}</span> 
									<span class="addr-tel">{{addr.tel}}</span> 
									<template  v-if="addr.isDefault">
										<span class="addr-default">默认地址</span>
									</template >
								</div>
								<div class="op-btns">
									<span></span> 
									<template  v-if="!addr.isDefault">
										<a href="javascript:;" @click="setDefaul(index)" class="setDefaul">设置为默认地址</a>
									</template >
									<a href="javascript:;" @click="editAddr(index)" class="edit">编辑</a>
									<a href="javascript:;" @click="removeAddr(index)" class="remove">删除</a>
								</div>
							</li>
							
						</ul>
						<button id="lookMore" @click="viewMoreAddrs()" class="btn-link">{{more}}</button>
					</div><!-- contentM102-->
				</div><!-- contentM101-->
			</div>
			<!-- receiveInfo -->
			
			
			<div id="payMethod">
				<div class="contentM10">
					<h3>支付方式</h3>
					<div class="contentM10">
					
						<div id="paySelect">
							<div @click="select('zhifubao')" id="zhifubao" role="0"  class="selectbtn">
								<span>支付宝</span>
								<b id="b"></b><!--选中标识  -->
							</div>
							
							<div @click="select('weixin')" id="weixin"  role="1" class="selectbtn">
								<span>微信</span>
							</div>
							
							<div @click="select('yinlian')" id="yinlian"  role="2" class="selectbtn">
								<span>银联</span>
							</div>
						</div><!-- paySelect -->
						
					</div><!--contentM10  inner-->
				</div><!--contentM10  -->
			
			</div>
			<!--payMethod  -->
			
			
			<div id='deliveryWay'>
				<div class="contentM10">
					<div class="t-new">
						<h3>配送方式</h3>
					</div>
					<div class="contentM10">
						<div id="waySelect">
							<div @click="select('shunfeng')" id="shunfeng"  role="0" class="selectbtn"><span>顺丰</span><b id="b"></b></div>
							<div @click="select('yunda')" id="yunda"  role="1" class="selectbtn"><span>韵达</span></div>
							<div @click="select('zhongtong')" id="zhongtong"  role="2" class="selectbtn"><span>中通</span></div>
							<div @click="select('yuantong')" id="yuantong"  role="3" class="selectbtn"><span>圆通</span></div>
							<div @click="select('shentong')" id="shentong"  role="4" class="selectbtn"><span>申通</span></div>
							<div @click="select('yzxb')" id="yzxb"  role="5" class="selectbtn"><span>邮政小包</span></div>
						</div><!-- waySelect  -->
					</div><!-- contentM102-->
				</div><!-- contentM101-->
			</div><!-- derliveryWay -->
				
				
			<div id="orderItemList">
				<div class="contentM10">
					<div class="t-new">
						<h3>物品清单</h3>
					</div>
					<div class="contentM10">
						<div id="yourChoose">
							<div id="yourPay">
								支付方式：{{payMethod}}
							</div>
							<div id="yourWay">
								邮寄方式：{{deliveryWay}}<br/>
								运费：<span class="price">￥{{deliveryCost}}</span>
							</div>
							<div>
								抵扣券：<span class="price">-￥{{coupon}}</span>
							</div>
							<div>费用总计：<span class="price">￥{{getAllCost()}}</span></div>
						</div><!-- yourChoose -->
						
						<div id="itemDetail">
							<div class="item" v-for="(cartForm,index) in cartForms">
								<div class="column goods">
									<img :src="getUrl(cartForm.folder)" :alt="cartForm.orderItem.goodsName" />
									<div class="aWordIntro" ></div>
								</div>
								<div class="column goodsNam">
									{{cartForm.orderItem.goodsName}}
								</div>
								<div class="column price" >
									已选购￥<span>{{cartForm.orderItem.onePrice}}</span> * <span>{{cartForm.orderItem.count}}</span>
								</div>
								<div class="column subSum" >
									小计￥{{cartForm.orderItem.subtotal}}
								</div>
							</div>
						</div><!-- itemDetail -->
					</div><!-- contentM102-->
				</div><!-- contentM101-->
			</div>
			<!-- orderItemList -->
			
			<div id="payheight">
				<div id="doPay">
					<button @click="doOrder()" class="btn btn-lg btn-danger  btn-block">提交订单</button>
				</div>
			</div>
			<!--  doPay-->
		</div><!-- main -->
	</section>
	
<!-- Modal -->
<div class="modal fade" id="addrModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增收货地址</h4>
      </div>
      <div class="modal-body">
      	<form id="newAddr" class="form-horizontal">
      	
		  <div class="form-group">
		    <label for="text" class="col-sm-2 control-label">收货人</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="text">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="" class="col-sm-2 control-label">所在地区</label>
		    <div class="col-sm-10">
			    <div class="info">
			     	<div id="area">
						<select id="s_province" name="s_province"></select>  
					    <select id="s_city" name="s_city" ></select>  
					    <select id="s_county" name="s_county"></select>
					    <script src="static/js/area.js" charset="utf8" type="text/javascript"></script>
					    <script type="text/javascript">_init_area();</script>
				    </div>
				    <div id="show"></div>
			    </div>
			</div><!-- col-sm-10 -->
		  </div><!-- form-group -->
		  
		  <div class="form-group">
		    <label for="" class="col-sm-2 control-label">详细地址</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="inputPassword3" >
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="" class="col-sm-2 control-label">手机号码</label>
		    <div class="col-sm-10">
		      <input type="phone" class="form-control" id="inputPassword3" >
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button @click="addAddr()" type="button" data-dismiss="modal" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div><!--model  -->


	<%@include file="../footer.jsp"%>