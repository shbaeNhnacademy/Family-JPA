package com.nhnacademy.jpa.family.domain.relation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipRequest {
    @Nullable
    private Integer familySerialNumber;
    @NotBlank
    private String relationShip;
}
