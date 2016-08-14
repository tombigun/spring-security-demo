<%@ page import="com.security.demo.handler.domain.BadCredentialsEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <jsp:include page="core/source.jsp"></jsp:include>
</head>


<body class="login_body">
<div class="login_main">
    <div class="login_main_cen">
        <div class="login_main_title">XX平台</div>

        <form action="${basePath}/j_spring_security_check" method="post">



            <div class="login_inpbox">
                用户名:<input type="text" name="j_username" id="username" value="admin">
            </div>
            <div class="login_inpbox">
                密  码:<input type="password" name="j_password" id="password" value="123456">
            </div>
            <div class="login_btn">
                <button type="submit">登 录</button>
            </div>
            <div id="err_msg" class="login_prompt">
                <%
                    String errorCode = request.getParameter("errorCode");

                    BadCredentialsEnum e = null;
                    if (errorCode != null) {
                        BadCredentialsEnum[] values = BadCredentialsEnum.values();
                        for (BadCredentialsEnum ee : values) {
                            if ((ee.getErrorCode() + "").equals(errorCode)) {
                                e = ee;

                                break;
                            }
                        }

                        if (e != null) {
                %>
                <span class="alert alert-danger"><%= e.getMsg() %></span>
                <%
                        }
                    }
                %>

            </div>
        </form>
    </div>
</div>
</div>
</body>


</html>

