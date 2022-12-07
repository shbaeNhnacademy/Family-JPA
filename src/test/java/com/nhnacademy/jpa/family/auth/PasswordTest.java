package com.nhnacademy.jpa.family.auth;

import com.nhnacademy.jpa.family.config.RootConfig;
import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
@Slf4j
public class PasswordTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoder() {
        String pwd = "1234";

        for (int i = 0; i < 10; i++) {
            String encode = passwordEncoder.encode(pwd);
            System.out.println("encode = " + encode);

        }

//        boolean matches = passwordEncoder.matches(pwd, encode);
//        boolean matches1 = passwordEncoder.matches(pwd, "$2a$10$ZdhiNeeg5N0oiCJqUNlIGurBMEqlRh1vvdFdzTOECVckWW8OgqoW6");
//        log.info("raw : {}  / encoded : {}", pwd, encode);
        // $2a$10$UAmyACK5WPoQVOMVS3vAOu8BmGFni2Y4EmiOdlKtAwxlqVJkIW7oK
//        assertThat(matches).isTrue();

    }

    @Test
    void testHashPassword() throws NoSuchAlgorithmException {
        String pwd = "1234";
        String encode = PasswordUtils.encode(pwd);
        log.info("{}", encode);
        //03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4
        assertThat(encode).isEqualTo("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
    }
}
