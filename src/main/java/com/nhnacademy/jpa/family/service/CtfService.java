package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.code.CtfType;

public interface CtfService {
    CtfIssue getCtfIssue(long confirmNum);

    Long getLatestConfirmNum(CtfType type);

    CtfIssue insertCtfIssue(CtfIssue issue);

}
