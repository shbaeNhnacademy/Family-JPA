package com.nhnacademy.jpa.family.repository.certificate;

import com.nhnacademy.jpa.family.entity.CtfIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CtfIssueRepository extends CtfIssueRepositoryCustom, JpaRepository<CtfIssue,Long> {
    List<CtfIssue> findCtfIssueByIssuer_SerialNumber(int residentSn);
    Page<CtfIssue> findCtfIssueByIssuer_SerialNumber(int residentSn, Pageable pageable);

}
