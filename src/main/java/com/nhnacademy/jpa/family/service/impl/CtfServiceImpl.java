package com.nhnacademy.jpa.family.service.impl;

import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import com.nhnacademy.jpa.family.exception.CertificateNotFoundException;
import com.nhnacademy.jpa.family.repository.certificate.CtfIssueRepository;
import com.nhnacademy.jpa.family.service.CtfService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CtfServiceImpl implements CtfService {
    private final CtfIssueRepository ctfIssueRepository;

    public CtfServiceImpl(CtfIssueRepository ctfIssueRepository) {
        this.ctfIssueRepository = ctfIssueRepository;
    }

    @Override
    public CtfIssue getCtfIssue(long confirmNum) {
        Optional<CtfIssue> byId = ctfIssueRepository.findById(confirmNum);
        return byId.orElseThrow(() -> new CertificateNotFoundException());
    }

    @Override
    public List<CtfIssue> getCtfIssuesByResidentSn(int residentSn) {
        return ctfIssueRepository.findCtfIssueByIssuer_SerialNumber(residentSn);
    }

    @Override
    public Long getLatestConfirmNum(CtfType type) {
        return ctfIssueRepository.getLatestConfirmNumByCertificateType(type);
    }

    @Override
    public CtfIssue insertCtfIssue(CtfIssue issue) {
        return ctfIssueRepository.save(issue);
    }

    @Override
    public Page<CtfIssue> viewCtfIssuesByIssuerSn(int sn, Pageable pageable) {
        return ctfIssueRepository.findCtfIssueByIssuer_SerialNumber(sn, pageable);
    }
}
