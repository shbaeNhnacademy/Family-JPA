package com.nhnacademy.jpa.family.controller.rest;

import com.nhnacademy.jpa.family.domain.ResidentRegisterRequest;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.BirthPlace;
import com.nhnacademy.jpa.family.exception.ValidationFailedException;
import com.nhnacademy.jpa.family.service.ResidentService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/residents")
public class ResidentRestController {

    private final ResidentService residentService;

    public ResidentRestController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping
    public ResidentRegisterRequest registerResident(@Valid @RequestBody ResidentRegisterRequest residentRegisterRequest,
                                     BindingResult bindingResult) {
        verifyValidation(bindingResult);
        System.out.println("residentRegisterRequest = " + residentRegisterRequest);

        return residentRegisterRequest;
    }

    private Resident getNewResident(ResidentRegisterRequest rq) {
        Integer serialNumber = residentService.getLatestSerialNumber();
        return new Resident(serialNumber,
                rq.getName(),
                rq.getRegistrationNumber(),
                rq.getGender(),
                rq.getBirthDate(),
                rq.getBirthPlace(),
                rq.getRegistrationAddress());
    }

    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //LocalDateTime.parse(rq.getBirthDate(), formatter)
    private static void verifyValidation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
    }

}
