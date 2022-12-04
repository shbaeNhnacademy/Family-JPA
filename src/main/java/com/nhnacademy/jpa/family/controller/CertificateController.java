package com.nhnacademy.jpa.family.controller;

import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto;
import com.nhnacademy.jpa.family.domain.relation.FamilyRelationViewDto;
import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import com.nhnacademy.jpa.family.exception.CertificateNotFoundException;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.repository.household.HouseholdCompositionRepository;
import com.nhnacademy.jpa.family.service.CtfService;
import com.nhnacademy.jpa.family.service.HouseholdService;
import com.nhnacademy.jpa.family.service.ResidentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

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

        Integer householderSn = householdService.getHouseholderSnByMemberSn(sn);
        if (Objects.isNull(householderSn)) {
            throw new CertificateNotFoundException();
        }
        Resident householder = residentService.getResidentBySn(householderSn);

        CtfType 주민등록등본 = CtfType.주민등록등본;
        CtfIssue issue = ctfService.insertCtfIssue(getCtfIssue(householder, 주민등록등본));
        model.addAttribute("ctf", issue);

        Household household =
                householdService.getHouseholdBySerialNumber(householderSn);
        HouseholderViewDto viewDto =
                householdService.getHouseHolderInfoBySerialNumber(householderSn);
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

    @GetMapping("/issue/{serialNum}")
    public String viewIssueList(Pageable pageable,
                                @PathVariable("serialNum") int sn,
                                Model model) {
        //TODO 발급목록 구현필요
        if (pageable.getPageSize() > WebConfig.PAGE_SIZE) {
            return "redirect:/ctf/issue/" + sn + "?page=0&size=" + WebConfig.PAGE_SIZE;
        }
        Page<CtfIssue> page = ctfService.viewCtfIssuesByIssuerSn(sn, pageable);
        model.addAttribute("page", page);
        model.addAttribute("issues", page.getContent());
        model.addAttribute("pageSize", WebConfig.PAGE_SIZE);
        model.addAttribute("serialNum", sn);
        return "/ctf/issueView";
    }

    private static void verifySerialNumber(int sn) {
        if (sn == 0) {
            throw new ResidentNotFoundException(sn);
        }
    }
}
