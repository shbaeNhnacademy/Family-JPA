package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto;
import com.nhnacademy.jpa.family.entity.Household;

import java.util.List;

public interface HouseholdService {
    Household findHouseholdBySerialNumber(int residentSn);

    HouseholderViewDto getHouseHolderInfoBySerialNumber(int residentSn);

    List<HouseholdCompositionDto> getCompositionInfoByHouseholdSerialNumber(int householdSn);
}
