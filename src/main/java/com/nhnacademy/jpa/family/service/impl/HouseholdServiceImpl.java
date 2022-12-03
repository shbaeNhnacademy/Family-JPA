package com.nhnacademy.jpa.family.service.impl;

import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto.HouseholderAddress;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.exception.HouseholdNotFoundException;
import com.nhnacademy.jpa.family.repository.household.HouseholdRepository;
import com.nhnacademy.jpa.family.service.HouseholdService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HouseholdServiceImpl implements HouseholdService {
    private final HouseholdRepository householdRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    public Household findHouseholdBySerialNumber(int residentSn) {
        Optional<Household> byId = householdRepository.findHouseholdByHouseholder_SerialNumber(residentSn);
        return byId.orElseThrow(() -> new HouseholdNotFoundException());
    }

    @Override
    public HouseholderViewDto getHouseHolderInfoBySerialNumber(int residentSn) {
        List<HouseholderDto> dtos = householdRepository.getHouseholderDtoBySerialNumber(residentSn);
        HouseholderDto householderDto = dtos.get(0); // 세대주 이름, 구성 사유, 일자 추출 용.

        List<HouseholderAddress> addresses = new ArrayList<>();
        for (HouseholderDto dto : dtos) {
            addresses.add(new HouseholderAddress(
                    dto.getYesOrNo(),
                    dto.getMovementAddress(),
                    dto.getMovementReportDate()));
        }

        return new HouseholderViewDto(
                householderDto.getName(),
                householderDto.getReason(),
                householderDto.getCompositionDate(),
                addresses);
    }

    @Override
    public List<HouseholdCompositionDto> getCompositionInfoByHouseholdSerialNumber(int householdSn) {
        return householdRepository.getCompositionDtoByHouseholdSerialNumber(householdSn);
    }
}
