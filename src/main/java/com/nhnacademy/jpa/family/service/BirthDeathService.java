package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.domain.birthDeath.BirthParentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.BirthResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.DeathResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.ReporterDto;
import com.nhnacademy.jpa.family.entity.code.BirthDeathType;

import java.util.List;

public interface BirthDeathService {
    DeathResidentDto getDeathTargetInfoByTargetSn(int targetSn);

    ReporterDto getDeathReporterInfoByReporterSn(int reporterSn);

    ReporterDto getReporterInfoByTargetSn(int targetSn, BirthDeathType type);


    BirthResidentDto getBirthTargetInfoByTargetSn(int targetSn);

    List<BirthParentDto> getBirthParentInfoByTargetSn(int targetSn);

}
