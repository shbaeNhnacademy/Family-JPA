package com.nhnacademy.jpa.family.config;

import com.nhnacademy.jpa.family.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasRole("USER")
                .antMatchers("/redirect-index").authenticated()
                .antMatchers("/ctf**").authenticated()
                .antMatchers("/household**").authenticated()
                .antMatchers("/residents**").authenticated()
                .anyRequest().permitAll();

        http.requiresChannel()
                .anyRequest().requiresInsecure();

        http.formLogin()
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginPage("/auth/login")
                .loginProcessingUrl("/login");

        http.logout();

        http.csrf();

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
}
