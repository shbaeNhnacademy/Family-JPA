package com.nhnacademy.jpa.family.domain.household;

import com.nhnacademy.jpa.family.entity.code.CompositionReason;
import com.querydsl.core.annotations.QueryProjection;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
public class HouseholderDto {
    private final String name;
    private final CompositionReason reason;
    private final LocalDate compositionDate;
    private final String movementAddress;
    private final String yesOrNo;
    private final LocalDate movementReportDate;

    @QueryProjection
    public HouseholderDto(String name, CompositionReason reason, LocalDate compositionDate, String movementAddress, String yesOrNo, LocalDate movementReportDate) {
        this.name = name;
        this.reason = reason;
        this.compositionDate = compositionDate;
        this.movementAddress = movementAddress;
        this.yesOrNo = yesOrNo;
        this.movementReportDate = movementReportDate;
    }
}
