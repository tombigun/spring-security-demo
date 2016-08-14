package com.security.demo.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeftAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectURL = request.getParameter("redirectURL");

        if (redirectURL != null && !redirectURL.trim().isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for (char ch : redirectURL.toCharArray()) {
                if (ch < 0x7F) sb.append(ch);
                else {
                    byte bytes[] = ("" + ch).getBytes("utf-8");
                    for (byte b : bytes) {
                        sb.append("%").append(String.format("%02x", b & 0xFF));
                    }
                }
            }

            redirectURL = sb.toString();
        }

        if (null != redirectURL)
            response.sendRedirect(redirectURL);
        else
            response.sendRedirect(request.getContextPath() + "/index.htm");
    }

}