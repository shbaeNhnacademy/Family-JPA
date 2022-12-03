package com.nhnacademy.jpa.family.controller;

import com.nhnacademy.jpa.family.domain.FamilyRelationDto;
import com.nhnacademy.jpa.family.entity.CtfIssue;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.CtfType;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.repository.certificate.CtfIssueRepository;
import com.nhnacademy.jpa.family.service.CtfService;
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

    private final CtfService ctfService;

    public CertificateController(ResidentService residentService, CtfService ctfService) {
        this.residentService = residentService;
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

        List<FamilyRelationDto> members = residentService.getFamilyMemberFromBase(base.getSerialNumber());
        model.addAttribute("base", base);
        model.addAttribute("members", members);
        return "/ctf/certificateView";
    }

    @GetMapping("/registration/{serialNum}")
    public String viewRegistrationCtf(@PathVariable("serialNum") int sn,
                                  Model model) {
        verifySerialNumber(sn);
        //TODO 아래 지우고 등본용으로 만들어얌
        Resident householder = residentService.getResidentBySn(sn);

        CtfType 주민등록등본 = CtfType.주민등록등본;
        CtfIssue issue = ctfService.insertCtfIssue(getCtfIssue(householder, 주민등록등본));

        model.addAttribute("ctf", issue);

//        List<FamilyRelationDto> members = residentService.getFamilyMemberFromBase(base.getSerialNumber());
//        model.addAttribute("base", base);
//        model.addAttribute("members", members);
        return "/ctf/certificateView";
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
