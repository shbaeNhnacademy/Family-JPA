package com.nhnacademy.jpa.family.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.jpa.family.entity.code.BirthPlace;
import com.nhnacademy.jpa.family.entity.code.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResidentRegisterRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String registrationNumber;
    @NotNull
    private Gender gender;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime birthDate;
    @NotNull
    private BirthPlace birthPlace;
    @NotBlank
    private String registrationAddress;
}
