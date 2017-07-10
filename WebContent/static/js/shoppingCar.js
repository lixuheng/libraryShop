function handler(data) {
	if (data.code == 'c1') {
		cartForm=data.list;
	} else {
		if (null != data.list && "notLogin" == data.list[0]) {
			window.wxc.xcConfirm("<h3>请先登录！</h3>", "info", {
				onClose : function() {
					window.location = basePath + "/login";
				}
			});
		} else {
			window.wxc.xcConfirm("<h3>请求失败！</h3>", "error", {
				onClose : function() {
					window.location = basePath + "/index";
				}
			});
		}
	}

}
//数据加载处理器
var cartForm= null;
//执行加载初始化数据
loadData("trade/shoppingCar", "pageNo=1&pageSize=10", handler, "json", "POST",false,true);


$(function() {
	var init = new Vue({
		el:"#cart",
		data:{
			cartForm:cartForm,
			doall: false,
			selectCount:0,
			totalPrice:0.00,
			haveSave:0.00
		},
		methods: {
		    convert : function (price) {
		        if (isNaN(price)) {
		            return price;
		        }
		        return price.toFixed(2);
		    },
		    url:function(url){
		    	return "file/display?name="+url;
		    },
		    getId:function(index){
		    	return "id"+index;
		    },
		    add : function(count) {
		    	count=this.change(count);
				return ++count;
			},
			sub : function(count) {
				count= this.change(count);
				return count = --count > 1 ? count : 1;
			},
			change : function(value) {
				if (isNaN(value))
					value = 1;
				if (value > 10000)
					value = 1;
				value = value >= 1 ? value : 1;
				return value;
			},
			calculate:function(price,count){
				return this.convert(price*count);
			},
			selectAll:function(){
				if(this.doall == false){
					$(".allSelect").prop("checked",true);
					$(".oneCheckBox").prop("checked",true);
					$(".item").css("background-color","#FFF4E8");
					this.selectCount=this.cartForm.length;
					this.doall = true;
					
					init.$data.totalPrice = 0.00;
					$(".idclass").each(function(index){
						console.log("index"+index);
						init.countTotalPrice("add",index);
					})
					
				}else{
					$(".allSelect").prop("checked",false);
					$(".oneCheckBox").prop("checked",false);
					$(".item").css("background-color","white");
					this.selectCount=0;
					this.doall = false;
					
					init.$data.totalPrice = 0.00;
					
				}
			},
			select:function(index){
				var id = "#id"+index;
				var back  = $(id).parent(".item");
				var obj = $(id).siblings(".checkbox").children().children();
				var bool =  obj.prop("checked");
				bool = undefined == bool?false:bool;
				console.info(bool);
				if(bool){
					obj.prop("checked",true);
					back.css("background-color","#FFF4E8");
					++this.selectCount;
					this.countTotalPrice("add",index);
				}else{
					obj.prop("checked",false);
					back.css("background-color","white");
					--this.selectCount;
					this.countTotalPrice("sub",index);
				}
			},
			countTotalPrice:function(mode,index){
				var id = "#id"+index;
				var subSum = $(id).siblings(".subSum").text();
				subSum = subSum.trim();
				subSum = subSum.substr(1,subSum.length);
				console.info("subSum-"+subSum);
				subSum = parseFloat(subSum);
				this.totalPrice = parseFloat(this.totalPrice);
				if(mode=="add"){
					this.totalPrice += subSum;
				}else{
					this.totalPrice -= subSum;
				}
				this.totalPrice =this.convert(this.totalPrice);
				//console.info("totalPrice"+this.totalPrice);
			}
			,
			remove:function(index){
				//alert("移除"+index);
				var id = "#id"+index;
				$(id).parent().remove();
			},
			settlement:function(){
				var param = null;
				var obj = $(".oneCheckBox:checked").parents(".checkbox").siblings(".quantity").children();
				param = '[';
				obj.each(function(index){
					orderItemId = $(this).children('input[name=orderItemId]').val( );
					count = $(this).children('input[name=count]').val( );
					goodsId = $(this).children('input[name=goodsId]').val( );
					param += '{"orderItemId":"'+orderItemId+'","goodsId":"'+goodsId+'","count":"'+count+'"},'
				});
				param = param.substr(0,param.length-1);
				param += ']';
				console.info(param);
				$.ajax({  
				    type : 'POST',  
				    cache : false,  
				    url : 'trade/createOrder',
				    dataType:'json',  
				    contentType:'application/json;charset=UTF-8',  
				    traditional:true,
				    //data : JSON.stringify(param),//这个是用来把js对象转成字符串。
				    data : param,
				    error: function(data){
				    	console.info(data);
				    },
				    success : function(data){
				    	//console.info(data);
				    	if(data.code=="c1"){
				    		//创建订单成功
				    		console.info("create order succuse");
				    		//提取orderId
				    		orderId = data.list[0];
				    		console.info(orderId);
				    		//提前准备settlement数据
				    		loadData("trade/settlement","orderId="+orderId+"&page=1&size=10",function(data){
				    			console.info(data);
				    			if(data.code=="c1"){
				    				var storage = sessionStorage;
				    				storage.setItem("swap",JSON.stringify(data.list));
				    				//重新请求访问 跳转
						    		window.location = storage.getItem("basePath") +"/trade/settlement";
				    			}else{
				    				window.wxc.xcConfirm('准备数据失败，刷新或者重试',"error");
				    			}
				    		},"json","POST")
				    		
				    	}else{
				    		console.info("fail");
				    	}
				    } 
				});
				
			}
		}
	})//vue.js
	//console.log(init.$data)
	//console.log(init.$data.doall)
	//监听滚动条
	$(window).scroll(function(e){
		//console.info($(window).scrollTop());//这个方法是当前滚动条滚动的距离
		//console.info($(window).height())//获取当前窗体的高度
		//console.info($(document).height())//获取当前文档的高度
		var p = ($(document).height()-$(window).height()-340);
		if(  $(window).scrollTop() >= p  ){//没到底部时
			//console.log("来啦");
			$(".settlement")
			.css("position","relative")
			.css("bottom","0px")
			.css("left","0px")
		}
		else{
			$(".settlement")
			.css("position","fixed")
			.css("bottom","0px")
			.css("left","0px")
		}
	})
	
})//read go



























/*//存jQuery实现
//重计前端显示价格
calculate = function(price,quantity){
	return price*quantity;
}
//改变前端显示的数量数量
change = function(value) {
	if (isNaN(value))
		value = 1;
	if (value > 10000)
		value = 1;
	value = value >= 1 ? value : 1;
	return value;
}
//begin 一系列数量价格监听
$(".chCount .sub").click(function(e){
	value = $(this).siblings('.text').val();
	value = change(value);
	value = --value > 1 ? value : 1;
	$(this).siblings('.text').val(value);
	
	price = $(this).parents('.quantity').siblings('.price').text();
	subSum = calculate(price,value);
	subSum = subSum.toFixed(2);
	$(this).parents('.quantity').siblings('.subSum').text(subSum);
});
$(".chCount .add").click(function(e){
	value = $(this).siblings('.text').val();
	value = change(value);
	++value;
	$(this).siblings('.text').val(value);
	
	price = $(this).parents('.quantity').siblings('.price').text();
	subSum = calculate(price,value);
	subSum = subSum.toFixed(2);
	$(this).parents('.quantity').siblings('.subSum').text(subSum);
});
$(".chCount .text").mouseout(function(e){
	//alert("xx");
	value = $(this).val();
	value = change(value);
	$(this).val(value);
	
	price = $(this).parents('.quantity').siblings('.price').text();
	subSum = calculate(price,value);
	subSum = subSum.toFixed(2);
	$(this).parents('.quantity').siblings('.subSum').text(subSum);
});
$(".chCount .text").focusout(function(e){
	//alert("xx");
	value = $(this).val();
	value = change(value);
	$(this).val(value);
	
	price = $(this).parents('.quantity').siblings('.price').text();
	subSum = calculate(price,value);
	subSum = subSum.toFixed(2);
	$(this).parents('.quantity').siblings('.subSum').text(subSum);
});
//end 一系列数量价格监听

//$("#id0").siblings(".checkbox").children().children().is(":checked");
//begin 监听选中事件
$("#allSelect").click(function(){
	var kg = $(".input-check input");
	if($(this).is(":checked")){
		//kg.attr("checked","checked");
		kg.each(function(){
			console.info("begin-"+$(this).attr("checked") );
			if(!$(this).is("checked")){
				$(this).attr("checked","checked");
			}
			console.info("result-"+ $(this).attr("checked") );
		})
	}else{
		kg.removeAttr("checked");
	}
})

*/

