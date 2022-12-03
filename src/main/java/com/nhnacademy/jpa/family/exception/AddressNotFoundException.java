package com.nhnacademy.jpa.family.exception;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException() {
        super("Not found household movement address");
    }
}
