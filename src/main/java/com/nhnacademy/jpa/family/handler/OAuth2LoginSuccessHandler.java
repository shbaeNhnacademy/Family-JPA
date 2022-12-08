package com.nhnacademy.jpa.family.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        List<GrantedAuthority> authorities = new ArrayList<>(principal.getAuthorities());

        HttpSession session = request.getSession(true);
        session.setAttribute("username", principal.getAttribute("email"));
        session.setAttribute("authority", authorities.get(0).getAuthority());
        response.sendRedirect("/redirect-index");
    }
}
