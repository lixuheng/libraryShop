<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//out.println(basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh_EN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="author" content="孟盛能">
<meta name="keywords" content="书籍,网店,借阅,购买,监测,评估,用户行为,可视化">
<meta name="website" content="www.bewweb.cn"/>
<base href="<%=basePath%>"/>
<!-- 样式 -->
<link href="static/lib/bootstrap/css/bootstrap-theme.min.css" charset="utf8" type="text/css" rel="stylesheet">
<link href="static/lib/bootstrap/css/bootstrap.min.css" charset="utf8" type="text/css" rel="stylesheet">
<link href="static/css/common.css" charset="utf8" type="text/css" rel="stylesheet">
<link href="static/css/layout.css" charset="utf8" type="text/css" rel="stylesheet">
<link href="static/lib/xcConfirm/css/xcConfirm.css" charset="utf8" type="text/css" rel="stylesheet">

<!-- 脚本 -->
<script src="static/lib/jQuery/jquery-3.1.1.min.js" charset="utf8" type="text/javascript" ></script>
<script src="static/lib/bootstrap/js/bootstrap.min.js" charset="utf8" type="text/javascript" ></script>
<script src="static/js/commen.js" charset="utf8" type="text/javascript" ></script>
<script src="static/lib/xcConfirm/js/xcConfirm.js" charset="utf8" type="text/javascript" ></script>
<script src="static/lib/vue/js/vue.min.js" charset="utf8" type="text/javascript" ></script>







