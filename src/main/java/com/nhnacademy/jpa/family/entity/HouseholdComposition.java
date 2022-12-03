package com.nhnacademy.jpa.family.entity;

import com.nhnacademy.jpa.family.entity.code.CompositionReason;
import com.nhnacademy.jpa.family.entity.code.Relationship;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "household_composition_resident")
public class HouseholdComposition {
    @EmbeddedId
    private Pk pk;

    @MapsId("householdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_serial_number")
    private Household household;

    @MapsId("memberSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_serial_number")
    private Resident member;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column(name = "household_relationship_code")
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @Column(name = "household_composition_change_reason_code")
    @Enumerated(EnumType.STRING)
    private CompositionReason changeReason;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "household_serial_number")
        private Integer householdId;

        @Column(name = "resident_serial_number")
        private Integer memberSerialNumber;
    }
}
