<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../head.jsp" %>
<script src="static/js/proofDetail.js" charset="utf8" type="text/javascript" ></script>
<title>用户证据详情</title>
</head>
<body>
<section class="bigbox">
	<table id="detail" class="table">
		<tr><th>证据名称</th><th>证据值</th</tr>
			<tr  v-for="(proof,index) in proofData">
			<td>{{proof.proofName}}</td>
			<td>{{proof.proofValue}}</td>
			</tr>
	</table>
</section>
</body>