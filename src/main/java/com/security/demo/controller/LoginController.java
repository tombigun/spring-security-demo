package com.security.demo.controller;

import com.security.demo.handler.domain.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(LoginUser.getCurrentLoginUser() != null) {
            response.sendRedirect("index.htm");
            return null;
        }

        return "login";
    }

}
