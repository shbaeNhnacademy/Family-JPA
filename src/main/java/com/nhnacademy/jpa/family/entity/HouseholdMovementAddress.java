package com.nhnacademy.jpa.family.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "household_movement_address")
public class HouseholdMovementAddress {
    public static final String LAST_YES = "y";
    public static final String LAST_NO = "n";

    @EmbeddedId
    private Pk pk;

    @MapsId("householdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_serial_number")
    private Household household;

    @Column(name = "house_movement_address")
    private String movementAddress;

    @Column(name = "last_address_yn")
    private String yesOrNo;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "household_serial_number")
        private Integer householdId;

        @Column(name = "house_movement_report_date")
        private LocalDate reportDate;
    }
}
