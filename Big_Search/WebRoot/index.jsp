<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	*{ padding:0px; margin:0px}
	body{
		text-align:center;
	}
	#headMenu{
	font-family:"宋体";
	font-size:36px;
	font-weight:900;
	margin-top:30px;
	color:#FF0000;
	}
	a:link {color: #0000CC}    /* 未访问的链接 */
	a:visited {color: #0000CC} /* 已访问的链接 */
	
</style>
<script type="text/javascript" src="jslib/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="jslib/jqueryauto.js"></script>
  </head>
  
  <body>
    <div id="headMenu">
		<img src="./images/baidu_sylogo1.gif">
	</div>
	<form action="show_search" method="post">
		<p style="font-size:16px; color: #0000CC; text-align:center;">
		<a href="#">新 闻</a>&nbsp&nbsp
		<a href="#" style="color: #000000;">网 页</a>&nbsp&nbsp
		<a href="#">贴 吧</a>&nbsp&nbsp
		<a href="#">知 道</a>&nbsp&nbsp
		<a href="#">音 乐</a>&nbsp&nbsp
		<a href="#">图 片</a>&nbsp&nbsp
		<a href="#">视 频</a>&nbsp&nbsp
		<a href="#">地 图</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		</p>
		<input type="text" size="60" style="height:30px; border: thin solid #000000" id="word" name="word"/>
		<input type="submit" id="sub" value="百度一下" style="height:35px;width:90px;"/>
		<div id="auto">
	
		</div>
	</form>
  </body>
</html>
