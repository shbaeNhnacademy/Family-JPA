package com.nhnacademy.jpa.family.service.impl;

import com.nhnacademy.jpa.family.entity.FamilyRelationship;
import com.nhnacademy.jpa.family.exception.RelationshipNotFoundException;
import com.nhnacademy.jpa.family.repository.FamilyRelationshipRepository;
import com.nhnacademy.jpa.family.service.FamilyRelationshipService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FamilyRelationshipServiceImpl implements FamilyRelationshipService {
    private final FamilyRelationshipRepository familyRelationshipRepository;

    public FamilyRelationshipServiceImpl(FamilyRelationshipRepository familyRelationshipRepository) {
        this.familyRelationshipRepository = familyRelationshipRepository;
    }

    @Override
    public FamilyRelationship getFamilyRelationship(FamilyRelationship.Pk pk) {
        Optional<FamilyRelationship> byId = familyRelationshipRepository.findById(pk);
        return byId.orElseThrow(()->new RelationshipNotFoundException());
    }

    @Override
    public FamilyRelationship saveRelationship(FamilyRelationship familyRelationship) {
        return familyRelationshipRepository.save(familyRelationship);
    }

    @Override
    public void deleteRelationship(FamilyRelationship familyRelationship) {
        familyRelationshipRepository.delete(familyRelationship);
    }


}
