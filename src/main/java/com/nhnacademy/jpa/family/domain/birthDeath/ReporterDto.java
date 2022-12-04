package com.nhnacademy.jpa.family.domain.birthDeath;

import com.nhnacademy.jpa.family.Utils;
import com.nhnacademy.jpa.family.entity.code.Relationship;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ReporterDto {
    private final String name;
    private final String registrationNumber;
    private final Relationship relationship;
    private final LocalDate reportDate;
    private final String email;
    private final String phone;

    @QueryProjection
    public ReporterDto(String name, String registrationNumber, Relationship relationship, LocalDate reportDate, String email, String phone) {
        this.name = name;
        this.registrationNumber = Utils.getMarkedNumber(registrationNumber);;
        this.relationship = relationship;
        this.reportDate = reportDate;
        this.email = email;
        this.phone = phone;
    }
}
