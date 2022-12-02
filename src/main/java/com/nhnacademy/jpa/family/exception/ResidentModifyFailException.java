package com.nhnacademy.jpa.family.exception;

public class ResidentModifyFailException extends RuntimeException{
    public ResidentModifyFailException(Exception ex) {
        super(ex);
    }
}
