package com.nhnacademy.jpa.family.repository;

import com.nhnacademy.jpa.family.domain.relation.FamilyRelationDto;
import com.nhnacademy.jpa.family.domain.SerialNumberOnly;
import com.nhnacademy.jpa.family.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident,Integer> {

    @Query("select fr.relationship as relationship, r.name as name, r.birthDate as birthDate, " +
            "r.registrationNumber as registrationNumber, r.gender as gender " +
            "from Resident r " +
            "inner join fetch FamilyRelationship fr on r.serialNumber = fr.pk.familySerialNumber " +
            "where fr.baseMember.serialNumber = :sn")
    List<FamilyRelationDto> getResidentBySnFromFamilyRelationship(@Param("sn") Integer sn);

    Optional<Resident> findByName(String name);

    SerialNumberOnly findFirstByOrderBySerialNumberDesc();

    Optional<Resident> findByAuthId(String authId);

}
