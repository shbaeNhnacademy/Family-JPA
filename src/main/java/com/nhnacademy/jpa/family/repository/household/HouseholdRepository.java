package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.domain.SerialNumberOnly;
import com.nhnacademy.jpa.family.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseholdRepository extends HouseholdRepositoryCustom, JpaRepository<Household, Integer> {
    SerialNumberOnly findFirstByOrderBySerialNumberDesc();

    Optional<Household> findHouseholdByHouseholder_SerialNumber(int residentSn);
}
