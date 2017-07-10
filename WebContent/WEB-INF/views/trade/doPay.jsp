<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %>
<title>必易为商城</title>
</head>
<body>
<section class="bigbox">
	<div class="contentM20">
		<div>
			<h4>应付金额</h4>
			<h5>￥${requestScope.shouldPay}</h5>
		</div>
		<form id="pay-form">
			<input type="hidden" name="orderId" value="${requestScope.orderId}"/>
			<label>输入金额：</label>
			<input type="text" size="10" name="shouldPay" />
			<input id="submit" type="button" value="确定"/>
		</form>
	</div>
	<script type="text/javascript">
		$("#submit").click(function(){
			loadData("trade/doPay",$("#pay-form").serialize(),function(data){
				if(data.code=="c1"){
					window.wxc.xcConfirm("成功","success",{
						onClose:function(){
							window.close();
						}
					});
				}else{
					window.wxc.xcConfirm("发生错误","error");
				}
			},"json","post");
		})
	</script>
</section>
</body>
