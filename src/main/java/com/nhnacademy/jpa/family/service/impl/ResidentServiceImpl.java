package com.nhnacademy.jpa.family.service.impl;

import com.nhnacademy.jpa.family.domain.FamilyRelationDto;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.repository.ResidentRepository;
import com.nhnacademy.jpa.family.service.ResidentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResidentServiceImpl implements ResidentService{
    private final ResidentRepository residentRepository;

    public ResidentServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public Resident getResidentBySn(int sn) {
        Optional<Resident> byId = residentRepository.findById(sn);
        return byId.orElseThrow(() -> new ResidentNotFoundException());
    }

    @Override
    public Resident getResidentByName(String name) {
        Optional<Resident> byId = residentRepository.findByName(name);
        return byId.orElseThrow(() -> new ResidentNotFoundException());
    }

    @Override
    public List<Resident> getResidents() {
        return residentRepository.findAll();
    }

    @Override
    public Page<Resident> viewResidentsBy(Pageable pageable) {
        return residentRepository.findAll(pageable);
    }

    @Override
    public List<FamilyRelationDto> getFamilyMemberFromBase(int baseMemberSn) {
        return residentRepository.getResidentBySnFromFamilyRelationship(baseMemberSn);
    }

    @Override
    public Integer getLatestSerialNumber() {
        return residentRepository.findFirstByOrderBySerialNumberDesc().getSerialNumber();
    }

    @Override
    public Resident insertResident(Resident resident) {
        return residentRepository.save(resident);
    }
}
