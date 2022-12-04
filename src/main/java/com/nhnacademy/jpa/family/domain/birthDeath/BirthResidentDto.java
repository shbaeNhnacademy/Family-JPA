package com.nhnacademy.jpa.family.domain.birthDeath;

import com.nhnacademy.jpa.family.entity.code.BirthPlace;
import com.nhnacademy.jpa.family.entity.code.Gender;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class BirthResidentDto {
    private final String name;
    private final Gender gender;
    private final LocalDateTime birthDate;
    private final BirthPlace birthPlace;
    private final String address;


    @QueryProjection
    public BirthResidentDto(String name, Gender gender, LocalDateTime birthDate, BirthPlace birthPlace, String address) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.address = address;
    }
}
