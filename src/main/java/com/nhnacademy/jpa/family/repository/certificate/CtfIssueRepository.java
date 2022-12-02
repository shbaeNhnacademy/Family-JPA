package com.nhnacademy.jpa.family.repository.certificate;

import com.nhnacademy.jpa.family.entity.CtfIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CtfIssueRepository extends CtfIssueRepositoryCustom, JpaRepository<CtfIssue,Long> {
}
