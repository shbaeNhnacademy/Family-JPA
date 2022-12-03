package com.nhnacademy.jpa.family.entity;

import com.nhnacademy.jpa.family.entity.code.CompositionReason;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "household")
public class Household {
    @Id
    @Column(name = "household_serial_number")
    private Integer serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_resident_serial_number")
    private Resident householder;

    @Column(name = "household_composition_date")
    private LocalDate compositionDate;

    @Column(name = "household_composition_reason_code")
    @Enumerated(EnumType.STRING)
    private CompositionReason reason;

    @Column(name = "current_house_movement_address")
    private String currentMovementAddress;

    public Household(Integer latersSerialNumber, Resident householder, LocalDate compositionDate, CompositionReason reason, String currentMovementAddress) {
        this.serialNumber = latersSerialNumber + 1;
        this.householder = householder;
        this.compositionDate = compositionDate;
        this.reason = reason;
        this.currentMovementAddress = currentMovementAddress;
    }
}
