<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="static/js/nav.js" charset="utf8" type="text/javascript" ></script>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<%=basePath%>"><s:message code="nav.logo"/></a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" style="height:20px;width:1300px">
      <ul class="nav navbar-nav">
      	<li class="dropdown">
      	<!-- style="width:260px;padding-left:10px;" -->
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><!-- <s:message code="nav.position"/>: --><span id="position">北京</span><span class="caret"></span></a>
          <ul id="selectLocal" class="dropdown-menu" style="width:260px;padding-left:10px;">
            <li>
            	<h4>直辖市</h4>
            	<div class="contentM10">
	            	<a href="javascript:;">北京</a>&nbsp;
		            <a href="javascript:;">上海</a>&nbsp;
		            <a href="javascript:;">天津</a>&nbsp;
		            <a href="javascript:;">重庆</a>&nbsp;
	            </div>
	        </li>
	        <li>
	            <h4>河北</h4>
	            <div class="contentM10">
	            	<a href="javascript:;">石家庄</a>&nbsp;
	            	<a href="javascript:;">唐山</a>&nbsp;
	            	<a href="javascript:;">秦皇岛</a>&nbsp;
	            	<a href="javascript:;">邯郸</a>&nbsp;
	            </div>
            </li>
          </ul>
        </li>
        <c:if test="${empty sessionScope.LOGGEDUSER}">
	        <li><a href="login"><s:message code="nav.login"/> 
	        <!-- <span class="sr-only">(current)</span>-->
	        </a> 
	        </li>
	        <li><a href="regist"><s:message code="nav.regist"/></a></li>
        </c:if>  
        <c:if test="${not empty sessionScope.LOGGEDUSER}" >
        	<li><a href="user/id/${sessionScope.LOGGEDUSER.userId}">
        		${sessionScope.LOGGEDUSER.uname}
        	</a></li>
        	<li><a href="user/exit">
        		安全退出
        	</a></li>
        </c:if>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><s:message code="nav.order"/> <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#"><s:message code="nav.doingOrder"/></a></li>
            <li><a href="#"><s:message code="nav.hisOrder"/></a></li>
            <li><a href="#"><s:message code="nav.invalidOrder"/></a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><s:message code="nav.borrow"/> <span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<li><a href="#"><s:message code="nav.borrowing"/></a></li>
            <li><a href="#"><s:message code="nav.hisBorrow"/></a></li>
          </ul>
        </li>
      </ul>
      <form id="search_form" class="navbar-form navbar-left">
        <div class="form-group">
          <input type="text" id="search_input" name="keywords" class="form-control" placeholder="<s:message code="nav.searchPlace"/>">
        </div>
        <input type="button" id="search" class="btn btn-default" value=<s:message code="nav.search"/>  />
      </form>
      <ul class="nav navbar-nav navbar-left">
        <li><a href="#"><s:message code="nav.friends"/></a></li>
        <li><a href="#"><s:message code="nav.bs"/></a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><s:message code="nav.navigation"/> <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#"><s:message code="nav.nav_pwd"/></a></li>
            <li><a href="#"><s:message code="nav.nav_receive_address"/></a></li>
            <li><a href="#"><s:message code="nav.nav_mycard"/></a></li>
            <li><a href="#"><s:message code="nav.nav_viewHis"/></a></li>
            <li><a href="#"><s:message code="nav.nav_costHis"/></a></li>
            <li role="separator" class="divider"></li>
          </ul>
        </li>
        <li><a href="#"><s:message code="nav.server"/></a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>