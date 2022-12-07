package com.nhnacademy.jpa.family.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login/gitHub")
public class OAuthController {
    private static String clientId = "9ed1a18558dd10895e8e";
    private static String clientSecret = "3c09ade672b7e6f0844173ab03f1b478a90cd1b4";

    @GetMapping
    public void responseLoginView(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location","");
//        response.sendRedirect();

    }
}
