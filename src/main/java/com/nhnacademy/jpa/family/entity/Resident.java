package com.nhnacademy.jpa.family.entity;

import com.nhnacademy.jpa.family.entity.code.BirthPlace;
import com.nhnacademy.jpa.family.entity.code.DeathPlace;
import com.nhnacademy.jpa.family.entity.code.Gender;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "resident")
@ToString(of = {"serialNumber", "name", "registrationNumber"})
public class Resident {
    @Id
    @Column(name = "resident_serial_number")
    private Integer serialNumber;

    @Column
    private String name;

    @Column(name = "resident_registration_number")
    private String registrationNumber;

    @Column(name = "gender_code")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "birth_place_code")
    @Enumerated(EnumType.STRING)
    private BirthPlace birthPlace;

    @Column(name = "registration_base_address")
    private String registrationAddress;

    @Column(name = "death_date")
    private LocalDateTime deathDate;

    @Column(name = "death_place_code")
    @Enumerated(EnumType.STRING)
    private DeathPlace deathPlace;

    @Column(name = "death_place_address")
    private String deathPlaceAddress;

    public Resident(Integer latestSerialNumber, String name, String registrationNumber, Gender gender, LocalDateTime birthDate, BirthPlace birthPlace, String registrationAddress) {
        this.serialNumber = latestSerialNumber + 1;
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.registrationAddress = registrationAddress;
    }


    public void setDeathInfo(LocalDateTime deathDate, DeathPlace deathPlace, String deathPlaceAddress) {
        this.deathDate = deathDate;
        this.deathPlace = deathPlace;
        this.deathPlaceAddress = deathPlaceAddress;
    }
}
