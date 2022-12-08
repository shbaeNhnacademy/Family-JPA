package com.nhnacademy.jpa.family.controller;

import com.nhnacademy.jpa.family.handler.OAuth2LoginSuccessHandler;
import com.nhnacademy.jpa.family.service.CustomAuth2UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class OAuthController {
    private final static String CLIENT_ID = "9ed1a18558dd10895e8e";
    private final static String CLIENT_SECRET = "3c09ade672b7e6f0844173ab03f1b478a90cd1b4";
    private final static String AUTHORIZATION_URI = "https://github.com/login/oauth/authorize";
    private final static String TOKEN_URI = "https://github.com/login/oauth/access_token";
    private final static String USER_INFO_URI = "https://api.github.com/user";
    private final static String clientName = "github";
    private final static String STATE = UUID.randomUUID().toString();
    private final RestTemplate restTemplate = new RestTemplate();

    private final UserDetailsService userDetailsService;

    public OAuthController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login/gitHub")
    public void redirectOauth2(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        String uriStr = "/oauth2/authorization/github";
        response.sendRedirect(uriStr);
    }

    @GetMapping("/oauth2/authorization/github")
    public void redirectAuthorizationUri(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        String uriStr = AUTHORIZATION_URI +
                "?response_type=code" +
                "&state=" + STATE +
                "&scope=" + "user" +
                "&client_id=" + CLIENT_ID;
        response.sendRedirect(uriStr);
    }

    @GetMapping("/login/oauth2/code/github")
    public void getGrantCode(HttpServletRequest request,
                             HttpServletResponse response) throws IOException, ServletException {
        String queryString = request.getQueryString();
        String[] split = queryString.split("&");

        String code = split[0].split("=")[1];
        String state = split[1].split("=")[1];

        if (!state.equals(STATE)) {
            return;
        }

        Map attrMap = exchangeForGithubAttr(requestToGithubForAccessToken(code));

        String email = (String) attrMap.get("email");
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        OAuth2User oAuth2User = new DefaultOAuth2User(userDetails.getAuthorities(), attrMap, "email");

        OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(oAuth2User, oAuth2User.getAuthorities(), clientName);
        settingSecurityContext(authenticationToken);

        new OAuth2LoginSuccessHandler().onAuthenticationSuccess(request, response, authenticationToken);
    }

    private void settingSecurityContext(OAuth2AuthenticationToken authenticationToken) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
    }

    private Map exchangeForGithubAttr(Map body) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(USER_INFO_URI).build();
        HttpHeaders headers = new HttpHeaders();
        String access_token = (String) body.get("access_token");
        headers.setBearerAuth(access_token);
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<Map> exchange = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, Map.class);
        Map exchangeBody = exchange.getBody();
        return exchangeBody;
    }

    private Map requestToGithubForAccessToken(String code) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", CLIENT_ID);
        parameters.add("client_secret", CLIENT_SECRET);
        parameters.add("code", code);

        ResponseEntity<Map> mapResponseEntity = new RestTemplate().postForEntity(TOKEN_URI, parameters, Map.class);
        Map body = mapResponseEntity.getBody();
        return body;
    }

}
