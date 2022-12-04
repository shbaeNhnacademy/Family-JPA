package com.nhnacademy.jpa.family.service.impl;

import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto.HouseholderAddress;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.entity.HouseholdMovementAddress;
import com.nhnacademy.jpa.family.exception.AddressNotFoundException;
import com.nhnacademy.jpa.family.exception.HouseholdNotFoundException;
import com.nhnacademy.jpa.family.repository.household.HouseholdCompositionRepository;
import com.nhnacademy.jpa.family.repository.household.HouseholdMovementAddressRepository;
import com.nhnacademy.jpa.family.repository.household.HouseholdRepository;
import com.nhnacademy.jpa.family.service.HouseholdService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HouseholdServiceImpl implements HouseholdService {
    private final HouseholdRepository householdRepository;
    private final HouseholdMovementAddressRepository movementAddressRepository;
    private final HouseholdCompositionRepository compositionRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository, HouseholdMovementAddressRepository movementAddressRepository, HouseholdCompositionRepository compositionRepository) {
        this.householdRepository = householdRepository;
        this.movementAddressRepository = movementAddressRepository;
        this.compositionRepository = compositionRepository;
    }

    @Override
    public Household getHouseholdById(int householdSn) {
        Optional<Household> byId = householdRepository.findById(householdSn);
        return byId.orElseThrow(() -> new HouseholdNotFoundException());
    }

    @Override
    public Household getHouseholdBySerialNumber(int residentSn) {
        Optional<Household> byId = householdRepository.findHouseholdByHouseholder_SerialNumber(residentSn);
        return byId.orElseThrow(() -> new HouseholdNotFoundException());
    }

    @Override
    public HouseholdMovementAddress getMovementByPk(HouseholdMovementAddress.Pk pk) {
        Optional<HouseholdMovementAddress> byId = movementAddressRepository.findById(pk);
        HouseholdMovementAddress householdMovementAddress = byId.orElseThrow(() -> new AddressNotFoundException());
        return householdMovementAddress;
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

    @Override
    public Integer getLatestSerialNumber() {
        return householdRepository.findFirstByOrderBySerialNumberDesc().getSerialNumber();
    }

    @Override
    public Integer getHouseholderSnByMemberSn(int memberSn) {
        return compositionRepository.getHouseholderSnByMemberSn(memberSn);
    }

    @Override
    public Household insertHousehold(Household household) {
        return householdRepository.save(household);
    }

    @Override
    public void deleteHousehold(Household household) {
        householdRepository.delete(household);
    }

    @Override
    public HouseholdMovementAddress saveMovement(HouseholdMovementAddress movementAddress) {
        return movementAddressRepository.save(movementAddress);
    }

    @Override
    public void deleteHouseholdMovementAddress(HouseholdMovementAddress movementAddress) {
        movementAddressRepository.delete(movementAddress);
    }

}
