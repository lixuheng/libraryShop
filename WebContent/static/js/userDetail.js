/** ********************杂************* */
// 预览地理位置
	var showArea = function(id){
		document.getElementById('show').innerHTML = 
			"<h4>" +document.getElementById('s_province').value + 	
		document.getElementById('s_city').value +  
		document.getElementById('s_county').value + "</h4>"
	}
	
	var contimueB = false;
	function continueOp(id){
		html = "<h4>成功了吗?  </h4> ";
		var success = true;
		window.wxc.xcConfirm(html,"info",{
			onOk:function(){
				success = !success;
			},
			onClose:function(){
				success = !success;
				if(success){
					contimueB = true;
					// console.info("成功"+id);
					// class="upPhone"
					subId = id.substr(id.length-5);
					console.log("subId="+subId);
					if(subId=='Phone'||subId=='Email'){
						loadData("user/getUserInfo","",function(data){
							console.log(data);
							$(".upPhone").text(data.list[0].phone);
							$(".upEmail").text(data.list[0].email);
						},"json","POST")
					}else if(id=='#askForMFPwd'){
						$("#phoneExt").show();
					}
				}else{
					console.info("失败"+id);
				}
			}
		});
	}
	/* =====================杂================== */
	
	
	
	function getAddressData(){
		loadData("trade/getAddrs","",function(data){
			if(data.code=="c1"){
				addressData = data.list;
				findFilter = addressData;
			}else{
				
			}
		},null,'post')
	}
	// 加载地址
	var addressData = null;
	var findFilter = null;
	getAddressData();

	function delAddr(id){
		window.wxc.xcConfirm("确定？","confirm",{
			onOk:function(){
				loadData("trade/delAddr","addressId="+id,function(data){
					console.info(data);
					if(data.code == 'c1'){
						window.wxc.xcConfirm("已删除","success");
					}
				},"json","post");
			},
			onCancel:function(){
				console.log("cancel"+id);
			}
			
		})

	}

	function addAddr(id){
		
	}

	function upAddr(id){
		
	}

	Array.prototype.remove = function(val) {
		  for(var i=0; i<this.length; i++) {
		    if(this[i] == val) {
		      this.splice(i, 1);
		      break;
		    }
		  }
		}


	//加载未完成的订单数据
	var settlementList = null;
	function getNowOrderData(page,size){
		if(page==null) page = 1;
		if(size == null) size = 10;
		loadData("trade/viewNoCommitOrder","page="+page+"&size="+size,function(data){
			console.info(data);
			if(data.code=="c1"){
				settlementList = data.list;
			}else{
				window.wxc.xcConfirm("发生错误","error");
			}
		},"json","post",false,true);
	}
	getNowOrderData(1,3);
	
$(function(){
	/*******************************整体控制开始****************************** */
	// 初始化显示
	$(".slide .switch").hide();
	$(".account .switch").first().show();
	$(".order .switch").first().show();
	$(".borrow .switch").first().show();
	$(".care .switch").first().show();
	
	// 监听a标记跳转到大类并实现切换盒子
	$(".menu li a").click(function(e){
		path = $(this).parents("div").children("h3").children("a").attr("href");
		// 锚记跳转
		window.location=storage.getItem("basePath")+"/"+path;
		key = $(this).attr("role");
		switch(key){
			// ///////////////账号
			case 'userInfo':
				$('#userInfo').siblings(".switch").hide();
				$('#userInfo').show();
				break;
			case 'accountSafe':
				$('#accountSafe').siblings(".switch").hide();
				$('#accountSafe').show();
				break;
			case 'accountAttach':
				$('#accountAttach').siblings(".switch").hide();
				$('#accountAttach').show();
				break;
			case 'addresses':
				$('#addresses').siblings(".switch").hide();
				$('#addresses').show();
				break;
				
				// ////////////////////////订单
			case 'nowOrder':
				$('#nowOrder').siblings(".switch").hide();
				$('#nowOrder').show();
				break;
			case 'history':
				$('#history').siblings(".switch").hide();
				$('#history').show();
				break;
			case 'invalid':
				$('#invalid').siblings(".switch").hide();
				$('#invalid').show();
				break;
			case 'allOrder':
				$('#allOrder').siblings(".switch").hide();
				$('#allOrder').show();
				break;
			// //////////////////////借阅
			case 'nowBorrow':
				$('#nowBorrow').siblings(".switch").hide();
				$('#nowBorrow').show();
				break;	
			case 'hisBorrow':
				$('#hisBorrow').siblings(".switch").hide();
				$('#hisBorrow').show();
				break;
				
			// //////////////////////关注
			case 'careObj':
				$('#careObj').siblings(".switch").hide();
				$('#careObj').show();
				break;
		}
	})// menu a click
	
	/* ============================整体控制结束============================================= */
	
	
	
	
	/** *************************用户表单相关开始******************* */
	
	// 添加信息表单
	$("#addMore input").attr("readonly","true");
	$("#save").hide();
	$("#addbtn").click(function(e){
		$("#tip").html("");
		$("#save").show();
		$("#addMore input").removeAttr("readonly");
		$("#addMore input[name=uname]").attr("readonly","true");
	});	
	$("#save >input").click(function(e){
		// console.info($("#user_form").serialize());
		success = function(data){
			if(data.code == "c1"){
				$("#addMore input").attr("readonly","true");
				$("#save").hide();
				$("#tip").html("<div class='font20 red'>保存成功!</div>");
				setTimeout(() => {
					$("#tip").html("");
				}, 1000);
			}else{
				window.wxc.xcConfirm("没有成功，请重试","error");
			}
		}
		loadData("user/update",$("#user_form").serialize(),success,"json","POST");
	})
	
	/*
	 * var reader = null; if(FileReader){ reader = new FileReader(); }else{
	 * console.info("不支持reader对象"); }
	 */
	
	/* 监听头像文件域 */
	$("#headFile").change(function(){
		$("#button").css("display","block");
		$("#button").val("点击保存");
		// $("#headForm").submit();
		// action="user/addHeadPic"
		/*
		 * reader.readAsBinaryString(document.getElementById("headFile").files[0]);
		 * //console.info(reader); reader.onload = function(){
		 * console.info(reader.result);
		 */
			var formData = new FormData($("#headForm")[0]);
			$.ajax({
				url:"user/addHeadPic",
				type:"post",
				dataType:"json",
				processData:false,
	            contentType:false,
				// contentType:"multipart/form-data",
				data:formData,
				error:function(data){
					console.log("error");
					console.info(data);
				},
				success:function(data){
					console.info(data);
					if(data.code == "c1"){
						$("#headForm img").attr("src",'file/display?find=up&name='+data.list[0]);
					}else{
						console.log(" ");
					}
				}
			})
		/* } */
	})
	// 保存头像
	$("#button").click(function(){
		if($("#button").val()!='点击保存'){
			return false;
		}
		success = function(data){
			console.log(data);
			if(data.code == 'c1'){
				$("#button").val("成功");
				$("#button").removeClass("btn-info");
				$("#button").addClass("btn-success");
				
			}else{
				$("#button").val("失败");
				$("#button").removeClass("btn-info");
				$("#button").addClass("btn-warning");
			}
			
		}
		loadData("user/saveHeads","",success,"json","post");
	})
	
	$("#changePhone").click(function(){
		window.open("user/phone?mode=change");
		setTimeout(function(){
			continueOp("#changePhone");
		},1000)
	})
	
	$("#checkPhone").click(function(){
		window.open("user/phone?mode=checkNew");
		setTimeout(function(){
			continueOp("#checkPhone");
		},1000)
	})
	
	$("#changeEmail").click(function(){
		window.open("user/mail?mode=change");
		setTimeout(function(){
			continueOp("#changePhone");
		},1000)
	})
	
	$("#checkEmail").click(function(){
		window.open("user/mail?mode=checkNew");
		setTimeout(function(){
			continueOp("#checkPhone");
		},1000)
	})
	
	// 调用展示地理位置
	document.getElementById('s_county').setAttribute('onchange','showArea()');
	$("input[type=radio]").first().prop("checked",true);
	// 初始化数据
	if(null!=$("#sexhidden").attr("role")){
		if("1" == $("#sexhidden").attr("role")){
			$("input[type=radio]").first().prop("checked",true);
			$("input[type=radio]").first().next().prop("checked",false);
		}else{
			$("input[type=radio]").first().prop("checked",false);
			$("input[type=radio]").first().next().prop("checked",true);
		}
	}
	
	
	document.getElementById('birthday').valueAsDate = new Date($("#birthHidden").attr("role"));
	var plist = null;
	if(null != $("#cityHidden").attr("role")){
		// plist = $("#cityHidden").attr("role").split(" ");
		plist = $("#cityHidden").text().split(" ");
		/*
		 * console.info($("#cityHidden").attr("role")); console.info(plist[0]+"
		 * "+plist[1]+" "+plist[2]+" "+plist[3]);
		 */
		show  = "";
		if(plist[0] !=null){
			// $("#s_province").val(plist[0]);
			show = plist[0];
		}
		if(plist[1] != null){
			// $("#s_city").val(plist[1]);
			show += plist[1];
		}
		if(plist[2] != null){
			// $("#s_county").val(plist[2]);
			show+=plist[2]
		}
		
	}
	$("#show").html("<h4>"+show+"</h4>");
	if(plist[3]!=null){
		$("input[name=birthCity]").val(plist[3]);
	}
	/* ============================用户表单相关结束================================== */

	

	
	/** *************密码相关开始************** */
	$("#phoneExt").hide();
	function checkPwd(id){
		var pwd = $(id).val();
		if(pwd.length ==0){
			return false;
		}
		if(pwd.length>0&&pwd.length<6){
			$(id).parent().next().html("<span class='red'>密码长度不够</span>");
			return false
		}else{
			$(id).parent().next().html("");
		}
		return true;
	}
	var sameB = false;
	function isSame(id,idCP){
		var pwd = $(id).val();
		var pwdCP = $(idCP).val();
		sameB = checkPwd(id)&&checkPwd(idCP);
		if(pwd!=pwdCP){
			$(id).parent().next().html("<span class='red'>两次密码输入不一致</span>");
			sameB = false;
			return false;
		}else{
			$(id).parent().next().html("");
		}
		return true;
	}
	var xorCode = null;
	function getXCode(){
		loadData("getXorCode","",function(data){
			if(data.code=='c1'){
				xorCode = data.list[0] ;
			}else{
				console.log("错误");
			}
		},"json","post","false")
	}
	getXCode();
	/* 事件 */
	
	$("#oldPwd").focusout(function(){
		checkPwd(this);
	})
	$("#oldPwd").mouseout(function(){
		checkPwd(this);
	})
	$("#newPwd").focusout(function(){
		checkPwd(this);
	})
	$("#newPwd").mouseout(function(){
		checkPwd(this);
	})
	$("#newPwdCP").focusout(function(){
		isSame("#newPwd","#newPwdCP");
	})
	$("#newPwdCP").mouseout(function(){
		isSame("#newPwd","#newPwdCP");
	})
	
	
	// 提交表单申请
	$("#askForMFPwd").click(function(){
		if(sameB){
			var oldPwd = xor($("#oldPwd").val(),xorCode);
			oldPwd = Bytes2Str(oldPwd);
			var newPwd = xor($("#newPwd").val(),xorCode);
			newPwd = Bytes2Str(newPwd);
			var param ="oldPwd="+oldPwd +"&newPwd="+ newPwd ;
			loadData("user/modifyPwd",param,function(data){
				// console.info(data);
				if(data.code == 'c1'){
					window.open(data.list[0]);
					setTimeout(function(){
						continueOp("#askForMFPwd");
					},1000)
				}else{
					getXCode();
					if(data.list!=null){
						window.wxc.xcConfirm(data.list[0],"error")
					}else{
						window.wxc.xcConfirm("密码不匹配，申请失败","error");
					}
				}
			},"json","post")
		}else{
			isSame("#newPwd","#newPwdCP");
		}
	})
	
	// 确认修改
	$("#phoneExt>button").click(function(){
		loadData("user/doModifyPwd","",function(data){
			// console.info(data);
			if(data.code == 'c1'){
				window.wxc.xcConfirm("修改成功","success");
				$("#phoneExt").hide();
				$("oldPwd").val("");
				$("newPwd").val("");
				$("newPwdCP").val("");
			}else{
				if(data.list!=null){
					window.wxc.xcConfirm(data.list[0],"error")
				}else{
					window.wxc.xcConfirm("修改失败","error");
				}
			}
		},"json","post")
	})
	/* =============密码相关结束================== */
	
	/** ******8*******收货地址相关************ */

	var addressVue  = new Vue({
		el:"#address",
		data:{
			addressData:findFilter,
			allPage:parseInt(addressData.length/5)+1,//pageSize=10
			pageSize:5,
			pageNo:1,//当前页
			begin:0,
			end:5
		},
		methods:{
			nextPage:function(){
				this.pageNo = this.checkPage(++this.pageNo);
			},
			prePage:function(){
				this.pageNo = this.checkPage(--this.pageNo);
			},
			checkPage:function(num){
				if(isNaN(num)||num<1){
					num = 1;
				}
				if(num>this.allPage){
					num = this.allPage;
				}
				this.begin = (num-1)*this.pageSize;
				this.end = (num)*this.pageSize;
				return num;
			},
			edit:function(id){
				upAddr(id);
			},
			del:function(id){
				for(i=0;i<this.addressData.length;i++){
					if(this.addressData[i].addressId == id){
						this.addressData.remove(this.addressData[i]);
						break;
					}
				}
				delAddr(id);
			}
		}
	})
	
	function doFilt(){
		//addressVue.$data.addressData;
		var findtxt = $("#addresses input[type=text]").val();
		if(findtxt=="" || null == findtxt){
			addressVue.$data.addressData  = addressData;
			return false ;
		}
		addressVue.$data.addressData = new Array();
		for(i=0 ,j=0 ; i<addressData.length; i++){
			if(addressData[i].userName == $("#addresses input[type=text]").val()){
				addressVue.$data.addressData[j] = addressData[i];
				++j;
			}
		}
		return false;
	}
	
	$("#addresses input[type=button]").click(function(){
		doFilt();
	})
	
	
	$("#addresses input[type=text]").on("keydown",function(evt){
		if(evt.which==13){//回车
			doFilt();
		}
	});
	
	$("#addresses input[type=text]").focusout(function(){
		if($(this).length==0){
			doFilt();
		}
	});
	$("#addresses input[type=text]").mouseout(function(){
		if($(this).length==0){
			doFilt();
		}
	});
	
	

	 /* ===============收货地址相关==========*/

	
	
 /******************************订单相关开始**********************************************/
	
	
	var nowOrder = new Vue({
		el:"#nowOrder",
		data:{
			settlementList:settlementList,
			pageNo:1,
			pageSize:3,
			allPage:1
		},
		methods:{
			url:function(url){
				return "file/display?name="+url;
			},
			jiesuan(orderId){
				loadData("trade/settlement","orderId="+orderId,function(data){
					if(data.code=="c1"){
						console.log("success");
						storage.setItem("swap",JSON.stringify(data.list));
						window.location.href="trade/settlement";
					}else{
						console.log("发生错误");
					}
					
				},"json","post",false);
			}
		}
	});
	
 /* =============================订单相关结束======================* */

})// ready go









