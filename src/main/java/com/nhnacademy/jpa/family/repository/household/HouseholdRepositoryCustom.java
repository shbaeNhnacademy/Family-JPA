package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.domain.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.HouseholderDto;

import java.util.List;

public interface HouseholdRepositoryCustom {
    List<HouseholderDto> getHouseholderDtoBySerialNumber(int sn);

    List<HouseholdCompositionDto> getCompositionDtoByHouseholdSerialNumber(int householdSn);
}
