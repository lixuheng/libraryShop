var storage = sessionStorage;
if (null == storage || typeof (storage) == 'undefined') {
	storage = localStorage;
}

var category = null;
loadData("category","",function(data){
	if(data.code =='c1'){
		category = data.list;
		console.info(category);
	}
},"json","get",false,false)



/********发送基础证据*************/
var os = getOS();
var ua = navigator.userAgent.toLowerCase();
var IPTOLocal = storage.getItem("IPTOLocal");
var ip = storage.getItem("ip");

function postBaseProof(){
	var requestParams = "&local="+IPTOLocal;
	requestParams += "&ip="+ip;
	requestParams += "&os="+ os;
	requestParams += "&ua="+ua;
	console.log(requestParams);
	loadData("saveBaseProof",requestParams,function(data){
		if(data.code=='c1'){
			console.info("base success");
		}else{
			console.info("base error");
		}
	},"json","post");
}

//执行发送基础数据
postBaseProof();


//目录服务
$(function() {
	var bookClass = new Vue({
		el : "#bookClass",
		data : {
			columes : [ {
				"name" : "小说",
				"is" : true
			}, {
				"name" : "历史",
				"is" : true
			}, {
				"name" : "传记",
				"is" : true
			} ]
		}
	});
	
	var navUl = new Vue({
		el:"#navUl",
		data:{
			category:category,
			active:-1
		},
		methods:{
			showCategoryBox:function(index){
				$("#mainShow").hide();
				$("#categoryid"+index).css("background","#ddd");
				this.active = index;
			},
			showMain:function(){
				$("#mainShow").show();
				this.active = -1;
				$(".categoryClass").css("background","none");
			},quback:function(index){
				$("#categoryid"+index).css("background","none");
			},getCategoryUrl(v1,v2,v3){
				var rs =  "goods/category?label="+(900000000+parseInt(v1)+parseInt(v2)+parseInt(v3));
				return rs;
			}
		}
	}) 
	
	
	$('#bookClass > li').first().addClass('active');
	$('#bookClass > li').click(function (e) {
		e.preventDefault();
		$('#bookClass > li').removeClass('active');
		$(this).addClass('active');
	})
	

})

