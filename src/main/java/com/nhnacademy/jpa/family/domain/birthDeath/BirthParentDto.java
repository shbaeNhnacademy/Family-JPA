package com.nhnacademy.jpa.family.domain.birthDeath;

import com.nhnacademy.jpa.family.Utils;
import com.nhnacademy.jpa.family.entity.code.Gender;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

@Value
public class BirthParentDto {
    private final String name;
    private final Gender gender;
    private final String registrationNumber;

    @QueryProjection
    public BirthParentDto(String name, Gender gender, String registrationNumber) {
        this.name = name;
        this.gender = gender;
        this.registrationNumber = Utils.getMarkedNumber(registrationNumber);
    }
}
