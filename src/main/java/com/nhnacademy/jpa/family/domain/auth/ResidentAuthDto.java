package com.nhnacademy.jpa.family.domain.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@ToString
public class ResidentAuthDto extends User implements Serializable {
    @Nullable
    private String email;
    private String name;
    private boolean hasSocial;

    public ResidentAuthDto(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean hasSocial) {
        super(username, password, authorities);
        this.hasSocial = hasSocial;
    }
}
