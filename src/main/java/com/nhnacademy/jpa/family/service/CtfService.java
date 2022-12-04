package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CtfService {
    CtfIssue getCtfIssue(long confirmNum);

    List<CtfIssue> getCtfIssuesByResidentSn(int residentSn);

    Long getLatestConfirmNum(CtfType type);

    CtfIssue insertCtfIssue(CtfIssue issue);

    Page<CtfIssue> viewCtfIssuesByIssuerSn(int sn, Pageable pageable) ;

}
