package com.nhnacademy.jpa.family.exception;

public class ResidentNotFoundException extends RuntimeException{

    public ResidentNotFoundException(int SerialNumber) {
        super("No." + SerialNumber + " Resident Not Found");
    }

    public ResidentNotFoundException(String name) {
        super("Resident " + name + " Not Found");
    }
}
