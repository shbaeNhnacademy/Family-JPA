package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderDto;

import java.util.List;

public interface HouseholdRepositoryCustom {
    List<HouseholderDto> getHouseholderDtoBySerialNumber(int residentSn);

    List<HouseholdCompositionDto> getCompositionDtoByHouseholdSerialNumber(int householdSn);
}
