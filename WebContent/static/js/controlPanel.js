function getOnlineUsers(){
	loadData("manager/getOnlineUser","",function(data){
		console.info(data.list[0]);
		if(data.code=="c1"){
			onlineUsers = data.list;
		}else{
			
		}
	},"json","get",false);
}

var onlineUsers = null;
getOnlineUsers();
if(onlineUsers==null){
	onlineUsers = new Array();
}
console.log(onlineUsers);

function viewDetail(){
	loadData("manager/proofData",$("#detail-form").serialize(),function(data){
		if(data.code=="c1"){
			storage.setItem("swap",JSON.stringify(data.list));
			window.open("manager/detail");
		}else{
			
		}
	},"json","get");
}


function viewChat(){
	loadData("manager/proofData",$("#chat-form").serialize(),function(data){
		if(data.code=="c1"){
			storage.setItem("swap",JSON.stringify(data.list));
			window.open("manager/chat");
		}else{
			
		}
	},"json","get");
}

$(function(){
	$("#offline");
	var online = new Vue({
		el:"#online",
		data:onlineUsers,
		methods:{
			detail:function(){
				viewDetail();
			},
			chat:function(){
				viewChat();
			}
		}
	});
	
})