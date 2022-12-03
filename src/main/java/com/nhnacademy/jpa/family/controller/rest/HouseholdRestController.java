package com.nhnacademy.jpa.family.controller.rest;

import com.nhnacademy.jpa.family.Utils;
import com.nhnacademy.jpa.family.domain.household.HouseholdRegisterRequest;
import com.nhnacademy.jpa.family.domain.household.MovementModifyRequest;
import com.nhnacademy.jpa.family.domain.household.MovementRegisterRequest;
import com.nhnacademy.jpa.family.domain.relation.ResidentRegisterRequest;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.entity.HouseholdMovementAddress;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.service.HouseholdService;
import com.nhnacademy.jpa.family.service.ResidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/household")
public class HouseholdRestController {
    private final HouseholdService householdService;
    private final ResidentService residentService;

    public HouseholdRestController(HouseholdService householdService, ResidentService residentService) {
        this.householdService = householdService;
        this.residentService = residentService;
    }

    @PostMapping
    public ResponseEntity<ResidentRegisterRequest> registerHousehold(@Valid @RequestBody HouseholdRegisterRequest householdRegisterRequest,
                                                                    BindingResult bindingResult) {
        Utils.verifyValidation(bindingResult);

        Household household = householdService.insertHousehold(getNewHousehold(householdRegisterRequest));
        return ResponseEntity.created(URI.create("/household/"+household.getSerialNumber())).build();
    }

    private Household getNewHousehold(HouseholdRegisterRequest hr) {
        Integer latestSerialNumber = householdService.getLatestSerialNumber();
        Resident householder = residentService.getResidentBySn(hr.getResidentSN());
        return new Household(
                latestSerialNumber,
                householder,
                hr.getDate(),
                hr.getReason(),
                hr.getAddress());
    }

    @DeleteMapping("/{householdSerialNumber}")
    public ResponseEntity deleteHousehold(@PathVariable("householdSerialNumber") int householdSn) {
        Household household = householdService.getHouseholdById(householdSn);
        householdService.deleteHousehold(household);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{householdSerialNumber}/movement")
    public ResponseEntity<MovementRegisterRequest> registerMovement(@PathVariable("householdSerialNumber") int householdSn,
                                                                    @Valid @RequestBody MovementRegisterRequest movementRegisterRequest,
                                                                    BindingResult bindingResult) {
        Utils.verifyValidation(bindingResult);
        HouseholdMovementAddress householdMovementAddress = getNewMovement(movementRegisterRequest, householdSn);
        HouseholdMovementAddress movementAddress = householdService.saveMovement(householdMovementAddress);
        return ResponseEntity.created(URI.create("/household/" + householdSn + "/movement")).build();
    }

    private HouseholdMovementAddress getNewMovement(MovementRegisterRequest mr, int householdSn) {
        Household household = householdService.getHouseholdById(householdSn);
        return new HouseholdMovementAddress(
                mr.getReportDate(),
                household,
                mr.getAddress(),
                mr.getLastAddressYN());
    }

    @PutMapping("/{householdSerialNumber}/movement/{reportDate}")
    public ResponseEntity<MovementModifyRequest> modifyMovement(@PathVariable("householdSerialNumber") int householdSn,
                                                                  @PathVariable("reportDate") String strDate,
                                                                  @Valid @RequestBody MovementModifyRequest modifyRequest,
                                                                  BindingResult bindingResult){
        Utils.verifyValidation(bindingResult);

        HouseholdMovementAddress movementByPk = getHouseholdMovementAddress(householdSn, strDate, modifyRequest);
        householdService.saveMovement(movementByPk);

        return ResponseEntity.ok(modifyRequest);
    }

    private HouseholdMovementAddress getHouseholdMovementAddress(int householdSn, String strDate, MovementModifyRequest modifyRequest) {
        HouseholdMovementAddress movementByPk = getMovementByPk(householdSn, strDate);
        movementByPk.setMovementAddress(modifyRequest.getAddress());
        movementByPk.setYesOrNo(modifyRequest.getLastAddressYN());
        return movementByPk;
    }

    private HouseholdMovementAddress getMovementByPk(int householdSn, String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate reportDate = LocalDate.parse(strDate, formatter);

        HouseholdMovementAddress.Pk pk = new HouseholdMovementAddress.Pk(householdSn, reportDate);
        HouseholdMovementAddress movementByPk = householdService.getMovementByPk(pk);
        return movementByPk;
    }

    @DeleteMapping("/{householdSerialNumber}/movement/{reportDate}")
    public ResponseEntity deleteMovement(@PathVariable("householdSerialNumber") int householdSn,
                                         @PathVariable("reportDate") String strDate) {
        HouseholdMovementAddress movementByPk = getMovementByPk(householdSn, strDate);
        householdService.deleteHouseholdMovementAddress(movementByPk);

        return ResponseEntity.noContent().build();
    }
}
