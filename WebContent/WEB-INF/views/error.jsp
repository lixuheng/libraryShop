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
		</div>
	</div>
<%@include file="footer.jsp" %>