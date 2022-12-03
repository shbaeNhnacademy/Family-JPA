package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.domain.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.HouseholderDto;

import java.util.List;

public interface HouseholdService {
    List<HouseholderDto> getHouseHolderInfoBySerialNumber(int serialNumber);

    List<HouseholdCompositionDto> getCompositionInfoByHouseholdSerialNumber(int householdSn);
}
