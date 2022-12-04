package com.nhnacademy.jpa.family.exception;

public class CertificateNotFoundException extends RuntimeException{

    public CertificateNotFoundException() {
        super("This Certificate does not exist");
    }
}
