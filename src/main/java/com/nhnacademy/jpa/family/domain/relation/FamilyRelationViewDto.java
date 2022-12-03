package com.nhnacademy.jpa.family.domain.relation;

import com.nhnacademy.jpa.family.entity.code.Gender;
import com.nhnacademy.jpa.family.entity.code.Relationship;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FamilyRelationViewDto {
    private final Relationship relationship;
    private final String name;
    private final LocalDateTime birthDate;
    private final String registrationNumber;
    private final Gender gender;
}
