package com.security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("index")
public class IndexController {

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("tagName", "index");
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.sendRedirect("user/index.htm");

        request.setAttribute("helloworld", "Hello World");

        return "user/index";
    }

}
