package com.nhnacademy.jpa.family.config;

import com.nhnacademy.jpa.family.handler.LoginSuccessHandler;
import com.nhnacademy.jpa.family.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity(debug = true)
@Configuration
@Slf4j
public class SecurityConfig  {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/redirect-index").authenticated()
                .antMatchers("/ctf/**").authenticated()
                .antMatchers("/household/**").authenticated()
                .antMatchers("/residents/**").authenticated()
                .anyRequest().permitAll();

        http.requiresChannel()
                .anyRequest().requiresInsecure();

        http.formLogin()
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginProcessingUrl("/login")
                .loginPage("/auth/login")
                .successHandler(loginSuccessHandler());


        http.logout()
                .invalidateHttpSession(true)
                .deleteCookies("SESSION");

        http.csrf().disable();

        http.sessionManagement()
                .sessionFixation().migrateSession();

        http.exceptionHandling()
                .accessDeniedPage("/error/403");
        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }



}
