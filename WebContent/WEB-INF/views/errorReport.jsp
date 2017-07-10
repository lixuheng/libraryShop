<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="head.jsp" %>
<title>出错了</title>
</head>
<body>
<%@include file="nav.jsp"%>
	<div class="bigbox" > 
		<div class="p20div">
			<h1><s:message code="error.error"/></h1>
			<hr/>
			<h4><s:message code="error.reference"/>:</h4>
			<h5>
				<c:if test="${not empty requestScope.reason }">
					${requestScope.reason }
				</c:if>
				<c:if test="${empty requestScope.reason }==null">
					<s:message code="error.dontKnow"/>
				</c:if>
			</h5>
			<br/>
			<h5><s:message code="error.contact"/></h5>
			<hr/>
			<center><h3><s:message code="error.tipdo"/></h3>
				<form method="post" action="report">
					<textarea rows="20" cols="100"></textarea>
					<br/>
					<input class="input" type="submit" value='<s:message code="error.submit"/>' />
				</form>
			</center>
		</div>
	</div>
<%@include file="footer.jsp" %>