package com.nhnacademy.jpa.family.service.impl;

import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import com.nhnacademy.jpa.family.exception.CertificateNotFoundException;
import com.nhnacademy.jpa.family.repository.certificate.CtfIssueRepository;
import com.nhnacademy.jpa.family.service.CtfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long getLatestConfirmNum(CtfType type) {
        return ctfIssueRepository.getLatestConfirmNumByCertificateType(type);
    }

    @Override
    public CtfIssue insertCtfIssue(CtfIssue issue) {
        return ctfIssueRepository.save(issue);
    }
}
