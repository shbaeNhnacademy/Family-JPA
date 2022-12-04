package com.nhnacademy.jpa.family.repository.birthDeath;

import com.nhnacademy.jpa.family.domain.birthDeath.*;
import com.nhnacademy.jpa.family.entity.BirthDeathReportResident;
import com.nhnacademy.jpa.family.entity.QBirthDeathReportResident;
import com.nhnacademy.jpa.family.entity.QFamilyRelationship;
import com.nhnacademy.jpa.family.entity.QResident;
import com.nhnacademy.jpa.family.entity.code.BirthDeathType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BirthDeathReportResidentRepositoryImpl
        extends QuerydslRepositorySupport implements BirthDeathReportResidentRepositoryCustom {

    public BirthDeathReportResidentRepositoryImpl() {
        super(BirthDeathReportResident.class);
    }

    @Override
    public DeathResidentDto getDeathResidentDtoByTargetSn(int targetResidentSn) {
        QResident resident = QResident.resident;
        QBirthDeathReportResident deathReportResident = QBirthDeathReportResident.birthDeathReportResident;

        return from(deathReportResident)
                .innerJoin(resident).on(deathReportResident.pk.serialNumber.eq(resident.serialNumber)).fetchJoin()
                .where(deathReportResident.pk.serialNumber.eq(targetResidentSn))
                .select(new QDeathResidentDto(
                        resident.name,
                        resident.registrationNumber,
                        resident.deathDate,
                        resident.deathPlace,
                        resident.deathPlaceAddress))
                .fetchOne();
    }

    @Override
    public ReporterDto getReporterDtoByReporterSn(int reporterSn) {
        QResident resident = QResident.resident;
        QBirthDeathReportResident deathReportResident = QBirthDeathReportResident.birthDeathReportResident;

        return from(deathReportResident)
                .innerJoin(deathReportResident.reporter, resident)
                .where(deathReportResident.reporter.serialNumber.eq(reporterSn))
                .select(new QReporterDto(
                        resident.name,
                        resident.registrationNumber,
                        deathReportResident.deathRelationship,
                        deathReportResident.reportDate,
                        deathReportResident.email,
                        deathReportResident.phoneNumber))
                .fetchOne();
    }

    @Override
    public ReporterDto getDeathReporterDtoByTargetSn(int targetResidentSn) {
        QResident resident = QResident.resident;
        QBirthDeathReportResident deathReportResident = QBirthDeathReportResident.birthDeathReportResident;

        return from(deathReportResident)
                .innerJoin(deathReportResident.reporter, resident)
                .where(deathReportResident.pk.serialNumber.eq(targetResidentSn)
                        .and(deathReportResident.pk.type.eq(BirthDeathType.사망)))
                .select(new QReporterDto(
                        resident.name,
                        resident.registrationNumber,
                        deathReportResident.deathRelationship,
                        deathReportResident.reportDate,
                        deathReportResident.email,
                        deathReportResident.phoneNumber))
                .fetchOne();
    }

    @Override
    public ReporterDto getBirthReporterDtoByTargetSn(int targetResidentSn) {
        QResident resident = QResident.resident;
        QBirthDeathReportResident birthReportResident = QBirthDeathReportResident.birthDeathReportResident;

        return from(birthReportResident)
                .innerJoin(birthReportResident.reporter, resident)
                .where(birthReportResident.pk.serialNumber.eq(targetResidentSn)
                        .and(birthReportResident.pk.type.eq(BirthDeathType.출생)))
                .select(new QReporterDto(
                        resident.name,
                        resident.registrationNumber,
                        birthReportResident.birthRelationship,
                        birthReportResident.reportDate,
                        birthReportResident.email,
                        birthReportResident.phoneNumber))
                .fetchOne();
    }

    @Override
    public BirthResidentDto getBirthResidentDtoByTargetSn(int targetResidentSn) {
        QResident resident = QResident.resident;
        QBirthDeathReportResident birthReportResident = QBirthDeathReportResident.birthDeathReportResident;

        return from(birthReportResident)
                .innerJoin(resident).on(birthReportResident.pk.serialNumber.eq(resident.serialNumber)).fetchJoin()
                .where(birthReportResident.pk.serialNumber.eq(targetResidentSn))
                .select(new QBirthResidentDto(
                        resident.name,
                        resident.gender,
                        resident.birthDate,
                        resident.birthPlace,
                        resident.registrationAddress))
                .fetchOne();
    }

    @Override
    public List<BirthParentDto> getBirthParentDtoByTargetSn(int targetResidentSn) {
        QResident resident = QResident.resident;
        QResident parent = new QResident("parent");
        QFamilyRelationship relationship = QFamilyRelationship.familyRelationship;
        QBirthDeathReportResident birthReportResident = QBirthDeathReportResident.birthDeathReportResident;

        List<Integer> fetch = from(birthReportResident)
                .innerJoin(resident).on(birthReportResident.pk.serialNumber.eq(resident.serialNumber)).fetchJoin()
                .innerJoin(relationship).on(resident.serialNumber.eq(relationship.pk.familySerialNumber))
                .where(birthReportResident.pk.serialNumber.eq(targetResidentSn))
                .select(relationship.pk.baseSerialNumber)
                .fetch();

        return from(parent)
                .where(parent.serialNumber.in(fetch))
                .select(new QBirthParentDto(
                        parent.name,
                        parent.gender,
                        parent.registrationNumber))
                .fetch();
    }
}
