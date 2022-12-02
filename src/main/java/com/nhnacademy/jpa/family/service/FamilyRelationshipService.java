package com.nhnacademy.jpa.family.service;

import com.nhnacademy.jpa.family.entity.FamilyRelationship;
import com.nhnacademy.jpa.family.entity.code.Relationship;

public interface FamilyRelationshipService {
    FamilyRelationship getFamilyRelationship(FamilyRelationship.Pk pk);
    FamilyRelationship saveRelationship(FamilyRelationship familyRelationship);

    void deleteRelationship(FamilyRelationship familyRelationship);

}
