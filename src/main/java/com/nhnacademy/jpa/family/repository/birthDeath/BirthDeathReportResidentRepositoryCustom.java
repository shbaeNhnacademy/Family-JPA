package com.nhnacademy.jpa.family.repository.birthDeath;

import com.nhnacademy.jpa.family.domain.birthDeath.BirthParentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.BirthResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.DeathResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.ReporterDto;

import java.util.List;

public interface BirthDeathReportResidentRepositoryCustom {
    DeathResidentDto getDeathResidentDtoByTargetSn(int targetResidentSn);

    ReporterDto getReporterDtoByReporterSn(int reporterSn);

    ReporterDto getDeathReporterDtoByTargetSn(int targetResidentSn);

    ReporterDto getBirthReporterDtoByTargetSn(int targetResidentSn);

    BirthResidentDto getBirthResidentDtoByTargetSn(int targetResidentSn);

    List<BirthParentDto> getBirthParentDtoByTargetSn(int targetResidentSn);

}
