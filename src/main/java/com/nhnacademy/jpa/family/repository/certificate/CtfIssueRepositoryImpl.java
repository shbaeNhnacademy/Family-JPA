package com.nhnacademy.jpa.family.repository.certificate;

import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.QCtfIssue;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CtfIssueRepositoryImpl extends QuerydslRepositorySupport implements CtfIssueRepositoryCustom {
    public CtfIssueRepositoryImpl() {
        super(CtfIssue.class);
    }

    @Override
    public Long getLatestConfirmNumByCertificateType(CtfType type) {
        QCtfIssue issue = QCtfIssue.ctfIssue;

        return from(issue)
                .where(issue.type.eq(type))
                .orderBy(issue.confirmNumber.desc())
                .select(issue.confirmNumber)
                .limit(1L)
                .fetchOne();
    }
}
