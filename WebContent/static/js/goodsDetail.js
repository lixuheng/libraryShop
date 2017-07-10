$(function() {

	var app = new Vue(
			{
				el : "#goodsDetal",
				data : {
					number : 1,
					shop : 'msn43',
					introSeen : true,
					commentSeen : false,
					rating : {
						good : 12,
						mid : 2,
						bad : 1
					},
					comment : {
						date : "2017-5-2 12:6:18",
						comment : "哈哈，我赚了",
						user : {
							uname : "msnmsn",
							degree : "黄金"
						}
					}
				},
				methods : {
					seeIntro : function() {
						this.introSeen = true;
						this.commentSeen = false;
					},
					seeComm : function() {
						this.introSeen = false;
						this.commentSeen = true;
						this.$nextTick(function() {
							showComments();
						})
					},
					add : function() {
						this.change();
						++this.number;
					},
					sub : function() {
						this.change();
						this.number = --this.number > 1 ? this.number : 1;
					},
					change : function() {
						if (isNaN(this.number))
							this.number = 1;
						if (this.number > 10000)
							this.number = 1;
						this.number = this.number >= 1 ? this.number : 1;
					}
				},
				computed : {
					grating : function() {
						return Math
								.round(this.rating.good
										/ (this.rating.good + this.rating.mid + this.rating.bad)
										* 100);
					},
					mrating : function() {
						return Math
								.round(this.rating.mid
										/ (this.rating.good + this.rating.mid + this.rating.bad)
										* 100);
					},
					brating : function() {
						return Math
								.round(this.rating.bad
										/ (this.rating.good + this.rating.mid + this.rating.bad)
										* 100);
					}
				}
			})

	$('#choooser-d-c > li').first().addClass('active');
	$('#choooser-d-c > li').click(function(e) {
		e.preventDefault();// 取消默认功能
		$('#choooser-d-c > li').removeClass('active');
		$(this).addClass('active');
	})

	menuFixed('gudingT');
	
	
	//加入购物车
	$("#joinCar").click(function(e){
		//console.info("#joinCar onclick");
		function handler(data){
			//console.info(data);
			if(data.code=="c1"){
				window.wxc.xcConfirm("<h3>添加到购物车成功！</h3><a style='font-size:20px' href='trade/shoppingCar'>去我的购物车</a>","success");
			}else{
				if(null!=data.list && "notLogin"==data.list[0]){
					window.wxc.xcConfirm("<h3>请先登录！</h3>","info",{onOk:function(){
						window.location = storage.getItem("basePath")+"/login";
					}});
				}else{
					window.wxc.xcConfirm("<h3>添加失败！</h3>","error");
				}
			}
		}
		params = $("#quantity_form").serialize();
		loadData('trade/joinShopCar',params,handler,"json","POST");
		//alert(params);
	})
	
	//加入关注
	$("#joinCare").click(function(e){
		//console.info("#joinCare onclick");
		function handler(data){
			console.log(data);
			if(data.code=="c1"){
				window.wxc.xcConfirm("<h3>添加到我的关注成功！</h3>","success");
			}else{
				if(null!=data.list && "notLogin"==data.list[0]){
					window.wxc.xcConfirm("<h3>请先登录！</h3>","info",{onOk:function(){
						window.location =  storage.getItem("basePath")+"/login";
					}});
				}else{
					window.wxc.xcConfirm("<h3>添加失败！</h3>","error");
				}
			}
		}
		params = $("#quantity_form").serialize();
		loadData('trade/joinCare',params,handler,"json","POST");
		//alert(params);
	})
	
	
	//借阅
	$("#borrow").click(function(e){
		//console.info("#joinCare onclick");
		function handler(data){
			console.log(data);
			if(data.code=="c1"){
				window.wxc.xcConfirm("<h3>借阅成功！</h3>","success");
			}else{
				if(null!=data.list && "notLogin"==data.list[0]){
					window.wxc.xcConfirm("<h3>请先登录！</h3>","info",{onOk:function(){
						window.location =  storage.getItem("basePath")+"/login";
					}});
				}else{
					window.wxc.xcConfirm("<h3>"+data.list[0]+"</h3>","error");
				}
			}
		}
		params = $("#quantity_form").serialize();
		loadData('trade/doBorrow',params,handler,"json","POST");
		//alert(params);
	})
	
	
	$("#down").click(function(e){
		/*function handler(data){
			console.log(data);
			if(data.code=="c1"){
				window.wxc.xcConfirm("<h3>借阅成功！</h3>","success");
			}else{
				if(null!=data.list && "notLogin"==data.list[0]){
					window.wxc.xcConfirm("<h3>请先登录！</h3>","info",{onOk:function(){
						window.location =  storage.getItem("basePath")+"/login";
					}});
				}else{
					window.wxc.xcConfirm("<h3>"+data.list[0]+"</h3>","error");
				}
			}
		}
		params = $("#down-form").serialize();
		loadData('file/download',params,null);
		*/
		$("#down-form").submit();
		//alert(params);
	})
	

})/*jquery 的尾巴*/

allComment = function(e) {
	// alert($('#alllist').html());
	$('#common_switch_box .list').hide();
	$("#alllist").show();

};
goodComment = function(e) {
	$('#common_switch_box .list').hide();
	$("#goodlist").show();

};
midComment = function(e) {
	$('#common_switch_box .list').hide();
	$("#midlist").show();

};
badComment = function(e) {
	$('#common_switch_box .list').hide();
	$("#badlist").show();

};

showComments = function() {
	allComment();
	$('#choooser-g-m-b > li').first().addClass('active');
	$('#choooser-g-m-b > li').click(function(e) {
		e.preventDefault();// 取消默认功能
		$('#choooser-g-m-b > li').removeClass('active');
		$(this).addClass('active');
	});
}



/*固定模块函数,监听滚动事件*/
function menuFixed(id){
	var obj = document.getElementById(id);
	var _getHeight = obj.offsetTop;
	window.onscroll = function(){
		changePos(id,_getHeight);
	}
}
function changePos(id,height){
	var obj = document.getElementById(id);
	var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
	//console.log("scrollTop: "+scrollTop+" height: "+height);
	if(scrollTop < height+600){
		obj.style.position = 'absolute';
		obj.style.right="0px";
		obj.style.top="30px";
		obj.style.marginRight="0px";
	}else{
		obj.style.position = 'fixed';
		obj.style.right="50%";
		obj.style.top="50px";
		obj.style.marginRight="-605px";
	}
}