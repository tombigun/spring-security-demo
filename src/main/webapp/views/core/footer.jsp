<%@ page import="com.security.demo.handler.ActiveUserService" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@page language="java" contentType="text/html;chartset=utf-8" pageEncoding="utf-8" %>

<%
    WebApplicationContext wc = RequestContextUtils.findWebApplicationContext(request);
    ActiveUserService aus = (ActiveUserService)wc.getBean("activeUserService");
    request.setAttribute("ActiveUserCount", aus.getActiveUserCount());
    request.setAttribute("AllSessionsCount", aus.getActiveUserAllSessionsCount());
%>

<div class="foot">
    <br class="foot_con">
        <div class="foot_con_left">底部</div>
        <div class="foot_con_right">
            在线用户数：${ActiveUserCount}人</br>

            在线用户数总会话：${AllSessionsCount}
        </div>
    </div>
</div>