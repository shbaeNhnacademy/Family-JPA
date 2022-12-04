package com.nhnacademy.jpa.family.controller;

import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.domain.birthDeath.BirthParentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.BirthResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.DeathResidentDto;
import com.nhnacademy.jpa.family.domain.birthDeath.ReporterDto;
import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderViewDto;
import com.nhnacademy.jpa.family.domain.relation.FamilyRelationViewDto;
import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.BirthDeathType;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import com.nhnacademy.jpa.family.entity.code.Gender;
import com.nhnacademy.jpa.family.exception.CertificateNotFoundException;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.service.BirthDeathService;
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
import java.util.Optional;

@Controller
@RequestMapping("/ctf")
public class CertificateController {
    private final ResidentService residentService;

    private final HouseholdService householdService;

    private final BirthDeathService birthDeathService;

    private final CtfService ctfService;

    public CertificateController(ResidentService residentService, HouseholdService householdService, BirthDeathService birthDeathService, CtfService ctfService) {
        this.residentService = residentService;
        this.householdService = householdService;
        this.birthDeathService = birthDeathService;
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

    @GetMapping("/death/{serialNum}")
    public String viewDeathCtf(@PathVariable("serialNum") int targetSn,
                                      Model model){
        verifySerialNumber(targetSn);

        Resident base = residentService.getResidentBySn(targetSn);

        CtfType 사망신고서 = CtfType.사망신고서;
        ctfService.insertCtfIssue(getCtfIssue(base, 사망신고서));

        DeathResidentDto targetDto = birthDeathService.getDeathTargetInfoByTargetSn(targetSn);
        ReporterDto reporterDto = birthDeathService.getReporterInfoByTargetSn(targetSn, BirthDeathType.사망);
        model.addAttribute("target", targetDto);
        model.addAttribute("reporter", reporterDto);

        return "/ctf/deathView";

    }

    @GetMapping("/birth/{serialNum}")
    public String viewBirthCtf(@PathVariable("serialNum") int targetSn,
                               Model model){
        verifySerialNumber(targetSn);

        Resident base = residentService.getResidentBySn(targetSn);

        CtfType 출생신고서 = CtfType.출생신고서;
        ctfService.insertCtfIssue(getCtfIssue(base, 출생신고서));

        BirthResidentDto targetDto = birthDeathService.getBirthTargetInfoByTargetSn(targetSn);
        List<BirthParentDto> parentDtos = birthDeathService.getBirthParentInfoByTargetSn(targetSn);
        Optional<BirthParentDto> OptFather = parentDtos.stream().filter(it -> it.getGender().equals(Gender.남)).findAny();
        Optional<BirthParentDto> OptMother = parentDtos.stream().filter(it -> it.getGender().equals(Gender.여)).findAny();

        ReporterDto reporterDto = birthDeathService.getReporterInfoByTargetSn(targetSn, BirthDeathType.출생);

        model.addAttribute("target", targetDto);
        model.addAttribute("reporter", reporterDto);
        model.addAttribute("father", OptFather.orElseThrow(() -> new ResidentNotFoundException("No.7's father")));
        model.addAttribute("mother", OptMother.orElseThrow(() -> new ResidentNotFoundException("No.7's mother")));

        return "/ctf/birthView";

    }

    private CtfIssue getCtfIssue(Resident base, CtfType type) {
        CtfIssue ctfIssue = new CtfIssue(base, type);
        Long latestConfirmNum = ctfService.getLatestConfirmNum(type);
        ctfIssue.setConfirmNumber(latestConfirmNum);

        return ctfIssue;
    }

    @GetMapping("/issue/{serialNum}")
    public String viewIssueList(Pageable pageable,
                                @PathVariable("serialNum") int sn,
                                Model model) {
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
