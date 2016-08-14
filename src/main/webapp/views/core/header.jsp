<%@ page import="com.security.demo.handler.domain.LoginUser" %>

<%@page language="java" contentType="text/html;chartset=utf-8" pageEncoding="utf-8" %>
<div class="header">
	<%
		LoginUser loginUser = LoginUser.getCurrentLoginUser();

		if(loginUser != null)
			request.setAttribute("loginUser", loginUser.getUser());
		else
			response.sendRedirect(request.getContextPath() + "/login.htm");

	%>

	<div class="top">
		<p>欢迎登陆</p>
		<p>${loginUser.userName}</p>

		<div>
			<a href="${basePath}/j_spring_security_logout"><span data-bind="html:name">退出系统</span></a>
		</div>
	</div>


</div>
