package com.nhnacademy.jpa.family.handler;

import com.nhnacademy.jpa.family.domain.auth.ResidentAuthDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        log.error("{}",principal.getUsername());
        List<GrantedAuthority> authorities = new ArrayList<>(principal.getAuthorities());

        HttpSession session = request.getSession(false);;

        session.setAttribute("username", principal.getUsername());
        session.setAttribute("authority", authorities.get(0).getAuthority());

    }
}
