package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.domain.auth.ResidentAuthDto;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.repository.ResidentRepository;
import com.nhnacademy.jpa.family.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final ResidentRepository residentRepository;

    public CustomUserDetailsService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Resident resident = residentRepository.findByAuthId(username)
                .orElseThrow(() -> new ResidentNotFoundException(username));

        ResidentAuthDto authDto = new ResidentAuthDto(resident.getAuthId(),
                resident.getAuthPwd(),
                Collections.singletonList(new SimpleGrantedAuthority("USER")),
                false);
        authDto.setEmail(resident.getAuthEmail());
        authDto.setName(resident.getName());

        User user = new User(resident.getAuthId(), resident.getAuthPwd(), Collections.singletonList(new SimpleGrantedAuthority("USER")));

        return user;
    }
}
