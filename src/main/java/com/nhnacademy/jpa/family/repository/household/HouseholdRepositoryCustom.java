package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.domain.HouseholderDto;

import java.util.List;

public interface HouseholdRepositoryCustom {
    List<HouseholderDto> getDtoBySerialNumber(int sn);
}
