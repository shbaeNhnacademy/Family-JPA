package com.nhnacademy.jpa.family.exception;

public class RelationshipNotFoundException extends RuntimeException{
    public RelationshipNotFoundException() {
        super("Relationship Not found");
    }
}
