package com.nhnacademy.jpa.family.controller;

import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.service.ResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Objects;

@Controller
@RequestMapping("/")
@Slf4j
public class ResidentController {
    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @GetMapping
    public String viewResidentMain(@SessionAttribute(value = "username", required = false) String username,
                                   Pageable pageable,
                                   Model model) {
        if (Objects.nonNull(username)) {
            if (pageable.getPageSize() > WebConfig.PAGE_SIZE) {
                return "redirect:/?page=0&size=" + WebConfig.PAGE_SIZE;
            }
            Page<Resident> page = residentService.viewResidentsBy(pageable);
            model.addAttribute("page", page);
            model.addAttribute("residents", page.getContent());
            model.addAttribute("pageSize", WebConfig.PAGE_SIZE);
        }
        return "/resident/residentMain";
    }

    @PostMapping("/{serialNumber}/delete")
    public String deleteResident() {
        //TODO 삭제 코드 구현 필요
        return "redirect:/";
    }


}
