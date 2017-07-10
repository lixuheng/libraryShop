var proofData = null;
proofData=JSON.parse(storage.getItem("swap"));

$(function(){
	
	console.log(proofData);
	var detail = new Vue({
		el:"#detail",
		data:proofData
	});
})//ready go