package com.nhnacademy.jpa.family.repository.birthDeath;

import com.nhnacademy.jpa.family.entity.BirthDeathReportResident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthDeathReportResidentRepository
        extends BirthDeathReportResidentRepositoryCustom, JpaRepository<BirthDeathReportResident,BirthDeathReportResident.Pk> {

}
