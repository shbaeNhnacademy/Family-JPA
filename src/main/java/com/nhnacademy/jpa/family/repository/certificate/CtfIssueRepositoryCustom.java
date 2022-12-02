package com.nhnacademy.jpa.family.repository.certificate;

import com.nhnacademy.jpa.family.entity.code.CtfType;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CtfIssueRepositoryCustom {
    Long getLatestConfirmNumByCertificateType(CtfType type);
}
