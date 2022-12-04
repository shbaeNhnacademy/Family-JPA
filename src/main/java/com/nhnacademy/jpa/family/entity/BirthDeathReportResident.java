package com.nhnacademy.jpa.family.entity;

import com.nhnacademy.jpa.family.entity.code.BirthDeathType;
import com.nhnacademy.jpa.family.entity.code.Relationship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "birth_death_report_resident")
@ToString
public class BirthDeathReportResident {
    @Id
    @Column(name = "resident_serial_number")
    private Integer serialNumber;

    @Id
    @Column(name = "birth_death_type_code")
    private BirthDeathType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_resident_serial_number")
    private Resident reporter;

    @Column(name = "birth_death_report_date")
    private LocalDate reportDate;

    @Column(name = "birth_report_qualifications_code")
    private Relationship birthRelationship;

    @Column(name = "death_report_qualifications_code")
    private Relationship deathRelationship;

    @Column(name = "email_address")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

}
