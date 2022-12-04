package com.nhnacademy.jpa.family.domain.birthDeath;

import com.nhnacademy.jpa.family.Utils;
import com.nhnacademy.jpa.family.entity.code.DeathPlace;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class DeathResidentDto {
    private final String name;
    private final String registrationNumber;
    private final LocalDateTime deathDate;
    private final DeathPlace deathPlace;
    private final String address;

    @QueryProjection
    public DeathResidentDto(String name, String registrationNumber, LocalDateTime deathDate, DeathPlace deathPlace, String address) {
        this.name = name;
        this.registrationNumber = Utils.getMarkedNumber(registrationNumber);
        this.deathDate = deathDate;
        this.deathPlace = deathPlace;
        this.address = address;
    }
}
