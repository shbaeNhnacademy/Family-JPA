package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.entity.HouseholdMovementAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdMovementAddressRepository
        extends JpaRepository<HouseholdMovementAddress,HouseholdMovementAddress.Pk> {

}
