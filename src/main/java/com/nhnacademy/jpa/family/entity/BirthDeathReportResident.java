package com.nhnacademy.jpa.family.entity;

import com.nhnacademy.jpa.family.entity.code.BirthDeathType;
import com.nhnacademy.jpa.family.entity.code.Relationship;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "birth_death_report_resident")
@ToString
public class BirthDeathReportResident {
    @EmbeddedId
    private Pk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_resident_serial_number")
    private Resident reporter;

    @Column(name = "birth_death_report_date")
    private LocalDate reportDate;

    @Column(name = "birth_report_qualifications_code")
    @Enumerated(EnumType.STRING)
    private Relationship birthRelationship;

    @Column(name = "death_report_qualifications_code")
    @Enumerated(EnumType.STRING)
    private Relationship deathRelationship;

    @Column(name = "email_address")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @ToString
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "resident_serial_number")
        private Integer serialNumber;

        @Column(name = "birth_death_type_code")
        @Enumerated(EnumType.STRING)
        private BirthDeathType type;
    }

    @Transient
    private Resident targetResident;

    public BirthDeathReportResident(Resident targetResident,Resident reporter, LocalDate reportDate, String phoneNumber) {
        this.reporter = reporter;
        this.reportDate = reportDate;
        this.phoneNumber = phoneNumber;
        this.targetResident = targetResident;
    }

}
