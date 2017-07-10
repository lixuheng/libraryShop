//加上选中状态
function doSelect(id){
	id="#"+id;
	//先清理选中的  msn 20175-11
	$(id).siblings(".selectbtn").each(function(){
		clear(this);
	})
	//$(id).children().next().prop('id') != 'b'
	if( $(id).children().next().prop('id') != 'b' ){
		//$(id).attr("check","true");
		$(id).children().after("<b id='b'></b>");
	}
	else{
		//$(id).attr("check","false");
		$(id).children().next().remove();
	}
}

//清除自制button的选中状态
function clear(dom){
	if($(dom).children().next().prop('id') == 'b'){
			$(dom).children().next().remove();
	}
}
//判断出那个自制button被选中了
function whoIsSelect(dom){
	var rs = null;
	$(dom).children("div").siblings().each(function(){
		if($(this).children("b").prop("id")=='b'){
			rs =  $(this).prop("id");
		}
	})
	return rs;
}

function addNewAddress(){
	console.info($("#newAddr").serialize());
	//loadData("trede/addAddr",newAddr.serialize()+"&",success,"json","POST");
	function success(data){
		console.info(data);
		if(data.Code=="c1"){
			//window.wxc.xcConfirm("成功","success");
		}else{
			console.info("fail")
		}
	}
	
}

$(function() {
	//var storage = sessionStorage;
	var list = storage.getItem("swap");
	list = JSON.parse(list);
	var settlement = list[0];
	var order = settlement.order;
	var cartForms  = settlement.cartForms;
	var addrs = settlement.addrs;
	/*console.log(settlement);
	console.log(order);
	console.log(cartForms[0].orderItem);
	console.log(addrs);*/
	var dopayApp = new Vue({
		el : "#main",
		data : {
			order:order,
			addrs:addrs,
			cartForms:cartForms,
			initNum : 1,
			more : "查看更多收货地址",
			order:order,
			payMethod:"支付宝",
			deliveryWay:"顺丰",
			deliveryCost:20.00,
			coupon:0
		},
		methods : {
			test : function() {
				alert("haha");
			},
			viewMoreAddrs : function() {
				if (this.initNum != this.addrs.length) {
					this.initNum = this.addrs.length;
					this.more = "收起";
				} else {
					this.initNum = 1;
					this.more = "查看更多收货地址";
				}
			}, 
			getUrl:function(url){
		    	return "file/display?name="+url;
		    },
			editAddr : function() {

			},
			removeAddr : function() {

			},
			setDefaul : function() {

			},
			select : function(id) {
				//选中
				doSelect(id);
				this.getPayMethod();
				this.getDeliveryWay();
			},
			getIndexId:function(index){
				return "id"+index;
			},
			getAddrMethod:function(){
				//var key = whoIsSelect("#paySelect");
			},
			getPayMethod:function(){
				var key = whoIsSelect("#paySelect");
				//console.info(key);
				switch(key){
					case "weixin":this.payMethod = "微信";break;
					case "yinlian":this.payMethod = "银联";break;
					default :this.payMethod = "支付宝";break;
				}
			},
			getDeliveryWay:function(){
				var key = whoIsSelect("#waySelect");
				//console.info(key);
				this.deliveryCost=8.00;
				switch(key){
					case "yunda":this.deliveryWay = "韵达";break;
					case "zhongtong":this.deliveryWay = "中通";break;
					case "yuantong":this.deliveryWay = "圆通";break;
					case "shentong":this.deliveryWay = "申通";break;
					case "yzxb":this.deliveryWay = "邮政小包";break;
					default :this.deliveryWay = "顺丰";this.deliveryCost=20;break;
				}
			},
			getAllCost:function(){
				var all = 0.00;
				for(i=0 ;i<cartForms.length; i++){
					orderItem = cartForms[i].orderItem;
					temp= parseFloat(orderItem.subtotal);
					all += temp;
				}
				all += parseFloat(this.deliveryCost);
				all -= parseFloat(this.coupon);
				console.info(all);
				return all.toFixed(2);
			},
			doOrder:function(){
				var param = null;
				var addr = $(".addr-info").first().text();
				var addrId = $(".addr-info").first().parents("li").prop("id");
				var payway = this.payMethod;
				var deliveryway = this.deliveryWay;
				//+"&address="+addr
				param ="orderId="+order.orderId+"&addrId="+addrId+"&deliveryWay="+deliveryway+"&payMethod="+payway;
				/*console.log(addr);
				console.log(payway);
				console.log(deliveryway);*/
				console.log("param--"+ param);
				
				loadData("trade/attachInfoToOrder",param,function(data){
					//console.info(data);
					if(data.code == "c1"){
						//跳往支付平台
						//alert("成功");
						window.open("trade/doPay?orderId="+order.orderId);
						window.wxc.xcConfirm("成功了吗？","success",{
							onClose:function(){
								window.location.href="index";
							}
						});
					}else{
						window.wxc.xcConfirm("发生错误","error");
					}
				},"json","post");
			}
		},
		computed : {
			test2 : function(addrId) {
				alert("wowwo");
			}
		}
	})// vue.js
	
	
	var addrModal  = new Vue({
		el:"#addrModal",
		data:{
			
		},methods:{
			addAddr : function() {
				alert("click");
				addNewAddress();
			}
		}
	})//vue.js
	// dopayApp.test();
	//window.wxc.xcConfirm("成功","success");
})// ready go


