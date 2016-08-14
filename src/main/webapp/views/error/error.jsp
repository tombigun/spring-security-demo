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
    <div class="bs-docs-content container" style="margin-top: 100px;">
        <div class="alert alert-danger">
            <button data-dismiss="alert" class="close" type="button">
                <i class="icon-remove"></i>
            </button>

            <strong>
                <i class="icon-remove"></i>
                错误!
            </strong>
            ${msg}
            <br>
        </div>
    </div>
</div>
</body>
</html>
