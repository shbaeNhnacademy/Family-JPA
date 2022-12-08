package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.repository.ResidentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final ResidentRepository residentRepository;

    public CustomUserDetailsService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Resident resident = null;
        List<SimpleGrantedAuthority> authorityList = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        if (username.contains("@")) {
            resident = residentRepository.findByAuthEmail(username)
                    .orElseThrow(() -> new ResidentNotFoundException(username));

            User user = new User(resident.getAuthEmail(), resident.getAuthPwd(), authorityList);
            return user;
        }else{
            resident = residentRepository.findByAuthId(username)
                    .orElseThrow(() -> new ResidentNotFoundException(username));

            User user = new User(resident.getAuthId(), resident.getAuthPwd(), authorityList);
            return user;
        }

    }
}
