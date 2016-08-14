<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html lang="zh-CN">
<head>
    <%
        request.setAttribute("DEFAULT_TITLE", "未找到页面");
    %>
    <jsp:include page="../core/source.jsp"/>
</head>
<body>
<div class="main">
    <div class="page-header">
        <h1>系统错误</h1>
    </div>
</div>
</body>
</html>
