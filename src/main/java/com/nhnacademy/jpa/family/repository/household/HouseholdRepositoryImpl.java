package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderDto;
import com.nhnacademy.jpa.family.domain.household.QHouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.QHouseholderDto;
import com.nhnacademy.jpa.family.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class HouseholdRepositoryImpl extends QuerydslRepositorySupport implements HouseholdRepositoryCustom{

    public HouseholdRepositoryImpl() {
        super(Household.class);
    }

    @Override
    public List<HouseholderDto> getHouseholderDtoBySerialNumber(int residentSn) {
        QHousehold household = QHousehold.household;
        QResident resident = QResident.resident;
        QHouseholdMovementAddress movementAddress = QHouseholdMovementAddress.householdMovementAddress;

        return from(household)
                .innerJoin(household.householder, resident)
                .leftJoin(movementAddress).on(household.serialNumber.eq(movementAddress.household.serialNumber))
                .where(resident.serialNumber.eq(residentSn))
                .select(new QHouseholderDto(
                        resident.name,
                        household.reason,
                        household.compositionDate,
                        movementAddress.movementAddress,
                        movementAddress.yesOrNo,
                        movementAddress.pk.movementReportDate))
                .fetch();
    }

    @Override
    public List<HouseholdCompositionDto> getCompositionDtoByHouseholdSerialNumber(int householdSn) {
        QResident resident = QResident.resident;
        QHouseholdComposition householdComposition = QHouseholdComposition.householdComposition;

        return from(householdComposition)
                .innerJoin(resident).on(householdComposition.pk.memberSerialNumber.eq(resident.serialNumber))
                .where(householdComposition.household.serialNumber.eq(householdSn))
                .select(new QHouseholdCompositionDto(
                        householdComposition.relationship,
                        resident.name,
                        resident.registrationNumber,
                        householdComposition.reportDate,
                        householdComposition.changeReason
                ))
                .fetch();
    }
}
