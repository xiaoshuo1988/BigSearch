<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.sql.ResultSet"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'admin.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<STYLE type="text/css">
		body {
			text-align: center;
		}
		.show {
			width: 700px;
		}
		a{TEXT-DECORATION:none}a:hover{TEXT-DECORATION:underline}
	</STYLE>
	</head>

	<body>
		<form action="">
			<table border="1" class="show" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="3" style="text-align: center;">
						<span style="font-size: 25px; font-family:arial;">后台管理</span>
					</td>
				</tr>
				<tr>
					<td width="20%" >
						编号
					</td>
					<td>
						题目
					</td>
					<td width="25%">
						操作
					</td>
				</tr>
				<%
					ResultSet rs = (ResultSet) session.getAttribute("rs");
					int i = 1;
					while (rs.next()) {
				%>
				<tr>
					<td><%=i%></td>
					<td>
						<input type="checkbox" />
						<%=rs.getString("title")%>
					</td>
					<td >
						<a href="background_pages/add.jsp">增加 </a> ||
						<a href="background_pages/delete.jsp">删除</a> ||
						<a href="background_pages/update.jsp">修改</a>
					</td>
				</tr>
				<%
					i++;
					}
				%>



			</table>
		</form>
	</body>
</html>
