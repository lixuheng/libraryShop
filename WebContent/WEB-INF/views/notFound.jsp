<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>
<title>没有可访问内容</title>
</head>
<body>
<%@include file="nav.jsp"%>
<div class="bigbox" > 
	<div class="p20div">
		<h1>${requestScope.notFound}</h1>
		<hr />
		<h5>${requestScope.contact }</h5>
	</div>
</div>
<%@include file="footer.jsp" %>