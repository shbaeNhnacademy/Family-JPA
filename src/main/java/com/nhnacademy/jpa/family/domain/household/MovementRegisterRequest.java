package com.nhnacademy.jpa.family.domain.household;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.jpa.family.entity.code.CompositionReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovementRegisterRequest {
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd",timezone = "Asia/Seoul")
    private LocalDate reportDate;
    @NotBlank
    private String address;
    @NotBlank
    @Size(max = 1)
    private String lastAddressYN;
}
