package com.nhnacademy.jpa.family.controller;

import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto;
import com.nhnacademy.jpa.family.domain.relation.FamilyRelationDto;
import com.nhnacademy.jpa.family.domain.relation.FamilyRelationViewDto;
import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.service.CtfService;
import com.nhnacademy.jpa.family.service.HouseholdService;
import com.nhnacademy.jpa.family.service.ResidentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ctf")
public class CertificateController {
    private final ResidentService residentService;

    private final HouseholdService householdService;

    private final CtfService ctfService;

    public CertificateController(ResidentService residentService, HouseholdService householdService, CtfService ctfService) {
        this.residentService = residentService;
        this.householdService = householdService;
        this.ctfService = ctfService;
    }

    @GetMapping("/relation/{serialNum}")
    public String viewRelationCtf(@PathVariable("serialNum") int sn,
                                  Model model) {
        verifySerialNumber(sn);

        Resident base = residentService.getResidentBySn(sn);

        CtfType 가족관계증명서 = CtfType.가족관계증명서;
        CtfIssue issue = ctfService.insertCtfIssue(getCtfIssue(base, 가족관계증명서));

        model.addAttribute("ctf", issue);

        List<FamilyRelationViewDto> members = residentService.getFamilyMemberFromBase(base.getSerialNumber());
        model.addAttribute("base", base);
        model.addAttribute("members", members);
        return "/ctf/relationView";
    }

    @GetMapping("/registration/{serialNum}")
    public String viewRegistrationCtf(@PathVariable("serialNum") int sn,
                                  Model model) {
        verifySerialNumber(sn);
        Resident householder = residentService.getResidentBySn(sn);
        Integer residentSn = householder.getSerialNumber();

        CtfType 주민등록등본 = CtfType.주민등록등본;
        CtfIssue issue = ctfService.insertCtfIssue(getCtfIssue(householder, 주민등록등본));

        model.addAttribute("ctf", issue);

        Household household =
                householdService.findHouseholdBySerialNumber(residentSn);
        HouseholderViewDto viewDto =
                householdService.getHouseHolderInfoBySerialNumber(residentSn);
        List<HouseholdCompositionDto> compositions =
                householdService.getCompositionInfoByHouseholdSerialNumber(household.getSerialNumber());

        model.addAttribute("viewDto", viewDto);
        model.addAttribute("compositions", compositions);
        return "/ctf/registrationView";
    }

    private CtfIssue getCtfIssue(Resident base, CtfType 가족관계증명서) {
        Long latestConfirmNum = ctfService.getLatestConfirmNum(가족관계증명서);
        CtfIssue ctfIssue = new CtfIssue(base, 가족관계증명서);
        ctfIssue.setConfirmNumber(latestConfirmNum);
        return ctfIssue;
    }

    private static void verifySerialNumber(int sn) {
        if (sn == 0) {
            throw new ResidentNotFoundException(sn);
        }
    }
}
