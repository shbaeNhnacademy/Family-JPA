package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.entity.HouseholdMovementAddress;

import java.util.List;

public interface HouseholdService {
    Household getHouseholdById(int householdSn);
    Household getHouseholdBySerialNumber(int residentSn);

    HouseholdMovementAddress getMovementByPk(HouseholdMovementAddress.Pk pk);

    HouseholderViewDto getHouseHolderInfoBySerialNumber(int residentSn);

    List<HouseholdCompositionDto> getCompositionInfoByHouseholdSerialNumber(int householdSn);

    Integer getLatestSerialNumber();

    Integer getHouseholderSnByMemberSn(int memberSn);

    Household insertHousehold(Household household);

    void deleteHousehold(Household household);

    HouseholdMovementAddress saveMovement(HouseholdMovementAddress movementAddress);

    void deleteHouseholdMovementAddress(HouseholdMovementAddress movementAddress);

}
