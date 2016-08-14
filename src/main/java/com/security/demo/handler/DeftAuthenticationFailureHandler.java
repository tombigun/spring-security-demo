package com.security.demo.handler;

import com.security.demo.handler.domain.BadCredentialsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class DeftAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeftAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if (isAjaxRequest(request)) {
            String jsonObject = "{\"message\":\"用户名或密码错误.\"," + "\"success\":false}";
            String contentType = "application/json";
            response.setContentType(contentType);
            PrintWriter out = response.getWriter();
            out.print(jsonObject);
            out.flush();
            out.close();
            return;
        }

        boolean isParentBadCredentialsException = (exception instanceof BadCredentialsException);
        boolean isChildBadCredentialsException = (exception.getCause() != null && exception.getCause() instanceof BadCredentialsException);

        int errorCode = 4;// 未知错误码
        String errorMsg = "";

        if (isParentBadCredentialsException || isChildBadCredentialsException) {// 认证失败
            String message = isParentBadCredentialsException ? exception.getMessage() : exception.getCause().getMessage();

            BadCredentialsEnum badCredentialsEnum = BadCredentialsEnum.findBadCredentialsEnum(message);

            if (badCredentialsEnum != null) {
                errorCode = badCredentialsEnum.getErrorCode();

                errorMsg = badCredentialsEnum.toString();
            } else {
                errorMsg = message;
            }
        } else if (exception instanceof DisabledException) {//用户被禁用或未启用
            errorCode = BadCredentialsEnum.USER_IS_NOT_SUPPORT.getErrorCode();
        } else {//其他异常导致了登录验证不成功
            String message = exception.getMessage();

            BadCredentialsEnum badCredentialsEnum = BadCredentialsEnum.findBadCredentialsEnum(message);

            if (badCredentialsEnum != null) {
                errorCode = badCredentialsEnum.getErrorCode();

                errorMsg = badCredentialsEnum.toString();
            } else {
                logger.error(exception.getMessage(), exception);// 记录错误日志

                errorMsg = message;
            }
        }

        logger.warn("Authentication Failure:{} {} " + errorCode, exception.getMessage(), errorMsg);

        response.sendRedirect(request.getContextPath() + "/login.htm?errorCode=" + errorCode);
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

}
