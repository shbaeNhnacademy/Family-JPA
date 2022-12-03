package com.nhnacademy.jpa.family.domain.household;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovementModifyRequest {
    @NotBlank
    private String address;
    @NotBlank
    @Size(max = 1)
    private String lastAddressYN;

}
