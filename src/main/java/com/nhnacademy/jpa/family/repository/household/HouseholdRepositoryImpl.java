package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.domain.HouseholderDto;
import com.nhnacademy.jpa.family.domain.QHouseholderDto;
import com.nhnacademy.jpa.family.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

public class HouseholdRepositoryImpl extends QuerydslRepositorySupport implements HouseholdRepositoryCustom{

    public HouseholdRepositoryImpl() {
        super(Household.class);
    }

    @Override
    public List<HouseholderDto> getDtoBySerialNumber(int sn) {
        QHousehold household = QHousehold.household;
        QResident resident = QResident.resident;
        QHouseholdMovementAddress movementAddress = QHouseholdMovementAddress.householdMovementAddress;
        QHouseholdMovementAddress_Pk pk = QHouseholdMovementAddress_Pk.pk;

        return from(household)
                .innerJoin(household.householder, resident)
                .leftJoin(movementAddress).on(household.serialNumber.eq(movementAddress.household.serialNumber))
                .where(resident.serialNumber.eq(sn))
                .select(new QHouseholderDto(
                        resident.name,
                        household.reason,
                        household.compositionDate,
                        movementAddress.movementAddress,
                        movementAddress.yesOrNo,
                        movementAddress.pk.movementReportDate))
                .fetch();
    }
}
