// 获取本地存储
/**
 * 存储列表
 * ip ip地址
 * IPTOLocal ip的地理位置
 * local 简要显示地址
 * basePath 系统基础路径
 * swap 临时交换数据(json)
 */
var storage = sessionStorage;
if (null == storage || typeof (storage) == 'undefined') {
	storage = localStorage;
}


//操作系统
function getOS(){
	// 获取用户代理
	var ua = navigator.userAgent;
	if (ua.indexOf("Windows NT 5.1") != -1)
		return "Windows XP";
	if (ua.indexOf("Windows NT 6.0") != -1)
		return "Windows Vista";
	if (ua.indexOf("Windows NT 6.1") != -1)
		return "Windows 7";
	if (ua.indexOf("Windows NT 10.0") != -1)
		return "Windows 10";
	if (ua.indexOf("iPhone") != -1)
		return "iPhone";
	if (ua.indexOf("iPad") != -1)
		return "iPad";
	if (ua.indexOf("Linux") != -1) {
		var index = ua.indexOf("Android");
		if (index != -1) {
			// os以及版本
			var os = ua.slice(index, index + 13);

			// 手机型号
			var index2 = ua.indexOf("Build");
			var type = ua.slice(index1 + 1, index2);
			return type + os;
		} else {
			return "Linux";
		}
	}
	return "unkownSystem";
}

//获取本系统基础路径
function getPath() {
	if(storage.getItem("basePath")!=null){
		return storage.getItem("basePath");
	}
	var location = (window.location + '').split('/');
	/*
	 * console.info("location[0]"+location[0]);
	 * console.info("location[1]"+location[1]);
	 * console.info("location[2]"+location[2]);
	 * console.info("location[3]"+location[3]);
	 * console.info("location[4]"+location[4]);
	 */
	var basePath = location[0] + '//' + location[2] + '/' + location[3];
	storage.setItem("basePath", basePath);
	return basePath;
}

function loadData(url, params, handler, returnType, reqMethod, isasync, iscache) {
	if (url == null)
		url = "/";// 请求路径
	if (params == null)
		params = "";
	if (returnType == null)
		returnType = "json";// 返回类型
	if (reqMethod == null)
		reqMethod = "GET";// 请求方式
	if (isasync == null)
		isasync = true;// 是否是异步
	if (iscache == null)
		iscache = false;// 是否使用缓存
	$.ajax({
		url : url,
		type : reqMethod,
		dataType : returnType,
		data : params,
		async : isasync,
		cache : iscache
	}).fail(function(data) {
		console.log("请求" + url + "失败！");
		console.log(data);
	}).done(handler);
}


function getPosition() {
	if (storage.getItem("IPTOLocal") != null) {
		return storage.getItem("IPTOLocal");
	}
	$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js',
			function(_result) {
				if (remote_ip_info.ret == '1') {
					/*
					 * alert('国家：' + remote_ip_info.country + '<BR>省：' +
					 * remote_ip_info.province + '<BR>市：' +
					 * remote_ip_info.city + '<BR>区：' +
					 * remote_ip_info.district + '<BR>ISP：' +
					 * remote_ip_info.isp + '<BR>类型：' + remote_ip_info.type + '<BR>其他：' +
					 * remote_ip_info.desc);
					 */
					var IPTOLocal = remote_ip_info.country + "|"
							+ remote_ip_info.province + "|"
							+ remote_ip_info.city + "|"
							+ remote_ip_info.district;
					// alert(IPTOLocal);
					storage.setItem("IPTOLocal", IPTOLocal);
					var local = remote_ip_info.city;
					storage.setItem("local", local);
				} else {
					console.log("未能获取地理位置");
				}
			});
}

function getIP() {
	var ip = null;
	if (storage.getItem("ip") != null) {
		return storage.getItem("ip");
	}
	var url = 'http://chaxun.1616.net/s.php?type=ip&output=json&callback=?&_='
			+ Math.random();
	$.getJSON(url, function(data) {
		ip = data.Ip;
		storage.setItem("ip", ip);
	});
	return ip;
}


/////////////////////初始化数据
getPosition();
getPath();
getIP();











//////////////////////////// 安全工具
function Bytes2Str(arr) {
	var str = "";
	for (var i = 0; i < arr.length; i++) {
		var tmp = arr[i].toString(16);
		if (tmp.length == 1) {
			tmp = "0" + tmp;
		}
		str += tmp;
	}
	return str;
}

function xor(userpwd, rand) {
	var pwdRs = new Array();
	if (userpwd.length > rand.length) {
		for (i = 0, j = 0; i < userpwd.length; i++) {
			pwdRs[i] = userpwd.charCodeAt(i) ^ rand.charCodeAt(j);
			// String.fromCharCode();
			++j;
			if (j == rand.length) {
				j = 0;
			}
		}
	} else {
		for (i = 0; i < userpwd.length; i++) {
			pwdRs[i] = userpwd.charCodeAt(i) ^ rand.charCodeAt(i);
		}
	}
	return pwdRs;
}












/////////////////////////websocket

// 创建和服务器的WebSocket 链接//onSuccess
var url = 'ws://localhost:8080/libraryShop/websocket';


var ws = null;
function createSocket() {
	ws = new WebSocket(url);
	ws.onopen = function() {
		console.log("open");
	}
	ws.onmessage = function(event) {
		if(typeof(event.data) == "string"){
			console.log("接收到文本消息");// 接收文本的最大长度为8KB
			console.log(event.data);
		}else if (event.data instanceof Blob) {
			console.log("blob message");
		}
	}
	ws.onclose = function(e) {
		console.log('onclose');
		console.info(e);
	}
	ws.onerror = function(e) {
		console.log('onerror');
		console.info(e);
	}
}

/*window.onbeforeunload = function() {
	closeWebSocket();
}*/

function closeWebSocket() {
	ws.close();
}

/*
function getWS(){
	if(ws==null&&storage.getItem("ws")==null){
		createSocket();
		storage.setItem("ws",JSON.stringify(ws));
	}else if(ws==null&&storage.getItem("ws")!=null){
		ws = JSON.parse(storage.setItem("ws",JSON.stringify(ws)));
	}else{
		
	}
}


getWS();*/






