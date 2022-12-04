package com.nhnacademy.jpa.family.repository;

import com.nhnacademy.jpa.family.config.RootConfig;
import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.domain.birthDeath.BirthParentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.BirthResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.DeathResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.ReporterDto;
import com.nhnacademy.jpa.family.repository.birthDeath.BirthDeathReportResidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class BirthDeathReportResidentRepositoryTest {
    @Autowired
    private BirthDeathReportResidentRepository reportResidentRepository;

    @Test
    void getDeathResidentDtoByResidentSn() {
        int residentSn = 1;
        DeathResidentDto deathResidentDto = reportResidentRepository.getDeathResidentDtoByTargetSn(residentSn);
        assertThat(deathResidentDto.getName()).isNotBlank();
        assertThat(deathResidentDto.getAddress()).isNotBlank();
    }

    @Test
    void getReporterDtoByReporterSn() {
        int reporterSn = 2;
        ReporterDto dtoByReporterSn = reportResidentRepository.getReporterDtoByReporterSn(reporterSn);
        assertThat(dtoByReporterSn.getName()).isNotBlank();
        assertThat(dtoByReporterSn.getRegistrationNumber()).isNotBlank();
    }

    @Test
    void getBirthReporterDtoByTargetSn() {
        int residentSn = 7;
        ReporterDto dtoByTargetSn = reportResidentRepository.getBirthReporterDtoByTargetSn(residentSn);
        assertThat(dtoByTargetSn.getName()).isNotBlank();
        assertThat(dtoByTargetSn.getRegistrationNumber()).isNotBlank();
    }

    @Test
    void getDeathReporterDtoByTargetSn() {
        int residentSn = 1;
        ReporterDto dtoByTargetSn = reportResidentRepository.getDeathReporterDtoByTargetSn(residentSn);
        assertThat(dtoByTargetSn.getName()).isNotBlank();
        assertThat(dtoByTargetSn.getRegistrationNumber()).isNotBlank();
    }

    @Test
    void getBirthResidentDtoByTargetSn() {
        int targetSn = 7;
        BirthResidentDto birthResidentDto = reportResidentRepository.getBirthResidentDtoByTargetSn(targetSn);
        assertThat(birthResidentDto.getName()).isNotBlank();
    }

    @Test
    void getBirthParentDtoByTargetSn() {
        int targetSn = 7;
        List<BirthParentDto> birthParentDtos = reportResidentRepository.getBirthParentDtoByTargetSn(targetSn);
        assertThat(birthParentDtos.size()).isEqualTo(2);
    }

}
