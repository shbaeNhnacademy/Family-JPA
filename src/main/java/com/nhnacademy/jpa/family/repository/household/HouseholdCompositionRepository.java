package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.entity.HouseholdComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HouseholdCompositionRepository
        extends JpaRepository<HouseholdComposition,HouseholdComposition.Pk> {

    @Query("select h.householder.serialNumber " +
            "from HouseholdComposition hc " +
            "inner join fetch Household h on hc.pk.householdId = h.serialNumber " +
            "where hc.pk.memberSerialNumber = :memberSn")
    Integer getHouseholderSnByMemberSn(@Param("memberSn") int memberSn);


}
