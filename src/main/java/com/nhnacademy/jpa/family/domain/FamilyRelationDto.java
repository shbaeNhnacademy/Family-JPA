package com.nhnacademy.jpa.family.domain;


import com.nhnacademy.jpa.family.entity.code.Gender;
import com.nhnacademy.jpa.family.entity.code.Relationship;

import java.time.LocalDateTime;

public interface FamilyRelationDto {
    Relationship getRelationship();

    String getName();

    LocalDateTime getBirthDate();

    String getRegistrationNumber();

    Gender getGender();

}
