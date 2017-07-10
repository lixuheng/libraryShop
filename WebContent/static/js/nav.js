
$(function() {
	if(storage.getItem("local")!=null){
		$("#position").text(storage.getItem("local"));
	}else{
		//默认值设在北京
		$("#position").text("北京");
	}
	
	$("#selectLocal a").click(function(){
		storage.setItem("local" , $(this).text() );
		$("#position").text($(this).text());
	})
	
	/*搜索查询*/
	$("#search").click(function() {
		submitSearch();
	})
	/*响应回车提交事件*/
	$("#search_form").on("keydown", function(evt) {
		if (evt.which == 13) {
			submitSearch();
			return false;
		}
	});
	
	
	/*user_id = '7975392919020510';
	loadData("user/id","id="+user_id,handler,"json","POST");
	function handler(data){
		user = data.list[0];
		var uerInfo_table = new Vue({
			el:"#uerInfo_table",
			data:user
		})
		//console.info(user);
	}*/
	
});

/*
$("#search_input").focusin(function(){
	$("#search_input").focusout(function(){
		len = $("#search_input").val().length;
		if (len < 1) {
			$("#search_input").attr("placeholder","填写错误!");
			return false;
		}else{
			$("#search_input").attr("placeholder","关键字!");
		}
	})
});*/

/*提交搜索表单*/
function submitSearch() {
	len = $("#search_input").val().length;
	if (len < 1) {
		return false;
	}
	loadData("goods/search", $("#search_form").serialize(), function(data) {
		//console.log(data);
		if (data.code == "c1") {
			va = true;
			storage.setItem("swap",JSON.stringify(data.list));
			//console.info(storage.getItem("swap"));
			window.location.href="goods/search";
		} else {
			$("#search_input").attr("placeholder","填写错误!");
			va = false;
		}
	}, "json", "POST");
	// 孟盛能 2017.4.25
	return false;
}