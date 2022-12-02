package com.nhnacademy.jpa.family.entity;

import com.nhnacademy.jpa.family.entity.code.Relationship;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "family_relationship")
@ToString
public class FamilyRelationship {
    @EmbeddedId
    private Pk pk;
    @MapsId("baseSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_resident_serial_number")
    private Resident baseMember;

    @Column(name = "family_relationship_code")
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "base_resident_serial_number")
        private Integer baseSerialNumber;

        @Column(name = "family_resident_serial_number")
        private Integer familySerialNumber;
    }

    @Transient
    private Resident familyMember;

    public FamilyRelationship(Resident baseMember, Resident familyMember, Relationship relationship) {
        this.baseMember = baseMember;
        this.familyMember = familyMember;
        this.relationship = relationship;
        this.setPk(new Pk(baseMember.getSerialNumber(), familyMember.getSerialNumber()));
    }
}
