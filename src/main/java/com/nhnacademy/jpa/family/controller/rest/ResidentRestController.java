package com.nhnacademy.jpa.family.controller.rest;

import com.nhnacademy.jpa.family.utils.ProgramUtils;
import com.nhnacademy.jpa.family.domain.relation.RelationshipRequest;
import com.nhnacademy.jpa.family.domain.relation.ResidentModifyRequest;
import com.nhnacademy.jpa.family.domain.relation.ResidentRegisterRequest;
import com.nhnacademy.jpa.family.entity.FamilyRelationship;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.Relationship;
import com.nhnacademy.jpa.family.exception.RelationshipNotFoundException;
import com.nhnacademy.jpa.family.exception.ResidentModifyFailException;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import com.nhnacademy.jpa.family.service.FamilyRelationshipService;
import com.nhnacademy.jpa.family.service.ResidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/residents")
public class ResidentRestController {

    private final ResidentService residentService;
    private final FamilyRelationshipService familyRelationshipService;

    public ResidentRestController(ResidentService residentService, FamilyRelationshipService familyRelationshipService) {
        this.residentService = residentService;
        this.familyRelationshipService = familyRelationshipService;
    }

    @PostMapping
    public ResponseEntity<ResidentRegisterRequest> registerResident(@Valid @RequestBody ResidentRegisterRequest residentRegisterRequest,
                                                                    BindingResult bindingResult) {
        ProgramUtils.verifyValidation(bindingResult);
        Resident resident = residentService.insertResident(getNewResident(residentRegisterRequest));
        return ResponseEntity.created(URI.create("/residents/"+resident.getSerialNumber())).build();
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

    @PutMapping("/{serialNumber}")
    public ResponseEntity<ResidentModifyRequest> modifyResident(@PathVariable("serialNumber") int sn,
                                                                @RequestBody ResidentModifyRequest residentModifyRequest) throws IllegalAccessException {
        Resident resident = residentService.getResidentBySn(sn);
        Resident modifyResident = residentService.applyModifyProperties(resident, residentModifyRequest);
        residentService.upadeteResident(modifyResident);
        return ResponseEntity.ok(residentModifyRequest);
    }

    @PostMapping("/{serialNumber}/relationship")
    public ResponseEntity<RelationshipRequest> registerRelationship(@PathVariable("serialNumber") int baseSn,
                                                                    @Valid @RequestBody RelationshipRequest relationshipRequest,
                                                                    BindingResult bindingResult) {
        ProgramUtils.verifyValidation(bindingResult);
        FamilyRelationship insertRelationship =
                settingFamilyRelationship(baseSn, relationshipRequest.getFamilySerialNumber(), relationshipRequest);
        return ResponseEntity
                .created(URI.create("/residents/" + insertRelationship.getBaseMember().getSerialNumber() + "/relationship"))
                .build();
    }


    @PutMapping("/{serialNumber}/relationship/{familySerialNumber}")
    public ResponseEntity<RelationshipRequest> modifyRelationship(@PathVariable("serialNumber") int baseSn,
                                   @PathVariable("familySerialNumber") int familySn,
                                   @Valid @RequestBody RelationshipRequest relationshipRequest,
                                   BindingResult bindingResult) {
        ProgramUtils.verifyValidation(bindingResult);
        settingFamilyRelationship(baseSn, familySn, relationshipRequest);
        return ResponseEntity.ok(relationshipRequest);
    }

    @DeleteMapping("/{serialNumber}/relationship/{familySerialNumber}")
    public ResponseEntity deleteRelationship(@PathVariable("serialNumber") int baseSn,
                                   @PathVariable("familySerialNumber") int familySn) {
        Resident baseMember = residentService.getResidentBySn(baseSn);
        Resident familyMember = residentService.getResidentBySn(familySn);
        FamilyRelationship.Pk pk =
                new FamilyRelationship.Pk(baseMember.getSerialNumber(), familyMember.getSerialNumber());
        familyRelationshipService.deleteRelationship(familyRelationshipService.getFamilyRelationship(pk));
        return ResponseEntity.noContent().build();
    }

    private FamilyRelationship settingFamilyRelationship(int baseSn, int familySn, RelationshipRequest relationshipRequest) {
        Resident baseMember = residentService.getResidentBySn(baseSn);
        Resident familyMember = residentService.getResidentBySn(familySn);
        Relationship familyRs = getRelationship(relationshipRequest);

        FamilyRelationship savedRelationship =
                familyRelationshipService.saveRelationship(new FamilyRelationship(baseMember, familyMember, familyRs));
        return savedRelationship;
    }

    private static Relationship getRelationship(RelationshipRequest relationshipRequest) {
        for (Relationship rs : Relationship.values()) {
            if (rs.getEnName().equals(relationshipRequest.getRelationShip())) {
                return rs;
            }
        }
        throw new RelationshipNotFoundException();
    }




    @ExceptionHandler(ResidentNotFoundException.class)
    public ResponseEntity<String> handleResidentNotFoundException(ResidentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(ResidentModifyFailException.class)
    public ResponseEntity<String> handleResidentModifyFailException(ResidentModifyFailException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(RelationshipNotFoundException.class)
    public ResponseEntity<String> handleRelationshipNotFoundException(RelationshipNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
