package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.domain.relation.FamilyRelationDto;
import com.nhnacademy.jpa.family.domain.ResidentModifyRequest;
import com.nhnacademy.jpa.family.domain.relation.FamilyRelationViewDto;
import com.nhnacademy.jpa.family.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResidentService {
    Resident getResidentBySn(int sn);

    Resident getResidentByName(String name);

    List<Resident> getResidents();

    Page<Resident> viewResidentsBy(Pageable pageable);

    List<FamilyRelationViewDto> getFamilyMemberFromBase(int baseMemberSn);

    Integer getLatestSerialNumber();

    Resident insertResident(Resident resident);

    void upadeteResident(Resident resident);

    Resident applyModifyProperties(Resident resident, ResidentModifyRequest modifyRequest) throws IllegalAccessException;
}
