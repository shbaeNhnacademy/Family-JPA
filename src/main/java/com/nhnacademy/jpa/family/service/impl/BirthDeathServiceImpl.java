package com.nhnacademy.jpa.family.service.impl;

import com.nhnacademy.jpa.family.domain.birthDeath.BirthParentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.BirthResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.DeathResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.ReporterDto;
import com.nhnacademy.jpa.family.entity.code.BirthDeathType;
import com.nhnacademy.jpa.family.repository.birthDeath.BirthDeathReportResidentRepository;
import com.nhnacademy.jpa.family.service.BirthDeathService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BirthDeathServiceImpl implements BirthDeathService {
    private final BirthDeathReportResidentRepository reportResidentRepository;

    public BirthDeathServiceImpl(BirthDeathReportResidentRepository reportResidentRepository) {
        this.reportResidentRepository = reportResidentRepository;
    }

    @Override
    public DeathResidentDto getDeathTargetInfoByTargetSn(int targetSn) {
        return reportResidentRepository.getDeathResidentDtoByTargetSn(targetSn);
    }

    @Override
    public ReporterDto getDeathReporterInfoByReporterSn(int reporterSn) {
        return reportResidentRepository.getReporterDtoByReporterSn(reporterSn);
    }

    @Override
    public ReporterDto getReporterInfoByTargetSn(int targetSn, BirthDeathType type) {
        if (type.equals(BirthDeathType.사망)) {
            return reportResidentRepository.getDeathReporterDtoByTargetSn(targetSn);
        }
        return reportResidentRepository.getBirthReporterDtoByTargetSn(targetSn);
    }

    @Override
    public BirthResidentDto getBirthTargetInfoByTargetSn(int targetSn) {
        return reportResidentRepository.getBirthResidentDtoByTargetSn(targetSn);
    }

    @Override
    public List<BirthParentDto> getBirthParentInfoByTargetSn(int targetSn) {
        return reportResidentRepository.getBirthParentDtoByTargetSn(targetSn);
    }

}
