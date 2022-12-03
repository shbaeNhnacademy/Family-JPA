package com.nhnacademy.jpa.family.domain.household;

import com.nhnacademy.jpa.family.entity.code.CompositionReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdRegisterRequest {
    @NotNull
    private Integer residentSN;
    @NotNull
    private LocalDate date;
    @NotNull
    private CompositionReason reason;
    @NotBlank
    private String address;
}
