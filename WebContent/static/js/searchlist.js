var goodsList = JSON.parse(storage.getItem("swap"));
//console.log(goodsList);

$(function(){
	
	var searchList = new Vue({
		el:"#searchResult",
		data: goodsList,
		methods:{
			getImgUrl:function(src){
				return "file/display?name="+src;
			},
			getGoodsUrl:function(url){
				return "goods/id/"+url;
			}
		}
	});
	
	
})//ready go