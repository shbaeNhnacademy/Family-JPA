package com.nhnacademy.jpa.family.entity;


import com.nhnacademy.jpa.family.entity.code.CtfType;
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
@Table(name = "certificate_issue")
@ToString
public class CtfIssue {

    @Id
    @Column(name = "certificate_confirmation_number")
    private Long confirmNumber;

    @ManyToOne
    @JoinColumn(name = "resident_serial_number")
    private Resident issuer;

    @Column(name = "certificate_type_code")
    @Enumerated(EnumType.STRING)
    private CtfType type;

    @Column(name = "certificate_issue_date")
    private LocalDate issueDate;

    public CtfIssue(Resident issuer, CtfType type) {
        this.issuer = issuer;
        this.type = type;
        this.issueDate = LocalDate.now();
    }

    public void setConfirmNumber(Long latestConfirmNumber) {
        this.confirmNumber = latestConfirmNumber + 1;
    }
}
