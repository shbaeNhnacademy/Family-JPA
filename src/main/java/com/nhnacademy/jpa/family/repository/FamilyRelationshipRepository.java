package com.nhnacademy.jpa.family.repository;

import com.nhnacademy.jpa.family.entity.FamilyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship,FamilyRelationship.Pk> {
}
