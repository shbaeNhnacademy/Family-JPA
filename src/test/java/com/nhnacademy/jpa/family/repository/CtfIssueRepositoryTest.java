package com.nhnacademy.jpa.family.repository;

import com.nhnacademy.jpa.family.config.RootConfig;
import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import com.nhnacademy.jpa.family.exception.CertificateNotFoundException;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.repository.certificate.CtfIssueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class CtfIssueRepositoryTest {
    @Autowired
    private CtfIssueRepository ctfIssueRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Test
    void findById() {
        long id = 1234567891011121L;
        Optional<CtfIssue> byId = ctfIssueRepository.findById(id);
        CtfIssue ctfIssue = byId.orElseThrow(() -> new CertificateNotFoundException());
        assertThat(ctfIssue.getConfirmNumber()).isEqualTo(id);
    }

    @Test
    void findLastConfirmNumByCtfType() {
        CtfType type = CtfType.가족관계증명서;
        Long num = ctfIssueRepository.getLatestConfirmNumByCertificateType(type);
        assertThat(num).isNotNull();
    }

    @Test
    void save_insert() {
        Optional<Resident> byId = residentRepository.findById(4);
        Resident resident = byId.orElseThrow(() -> new ResidentNotFoundException(4));
        CtfType 가족관계증명서 = CtfType.가족관계증명서;
        CtfIssue ctfIssue = new CtfIssue(resident, 가족관계증명서);
        Long latestConfirmNum = ctfIssueRepository.getLatestConfirmNumByCertificateType(가족관계증명서);
        ctfIssue.setConfirmNumber(latestConfirmNum);
        CtfIssue save = ctfIssueRepository.save(ctfIssue);

        assertThat(save.getConfirmNumber()).isEqualTo(latestConfirmNum + 1);
        assertThat(save.getIssuer()).isEqualTo(resident);
    }

    @Test
    void findCtfIssueByIssuer_SerialNumber() {
        int residentSn = 4;
        List<CtfIssue> ctfIssues = ctfIssueRepository.findCtfIssueByIssuer_SerialNumber(residentSn);
        assertThat(ctfIssues.size() > 0).isTrue();
    }

    @Test
    void findCtfIssueByIssuer_SerialNumber_pageable() {
        int residentSn = 4;
        Pageable pageable = PageRequest.of(0, 4);
        Page<CtfIssue> issuePage = ctfIssueRepository.findCtfIssueByIssuer_SerialNumber(residentSn, pageable);
        assertThat(issuePage.getContent().size() > 0).isTrue();
    }
}
