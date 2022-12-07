package com.nhnacademy.jpa.family.utils;

import com.nhnacademy.jpa.family.exception.ValidationFailedException;
import org.springframework.validation.BindingResult;

public class ProgramUtils {

    private ProgramUtils() {
        throw new IllegalStateException("Utility class");}

    public static final String YES = "Y";
    public static final String NO = "N";
    public static String getMarkedNumber(String registrationNumber) {
        StringBuilder sb = new StringBuilder();
        String[] split = registrationNumber.split("-");
        sb.append(split[0]).append("-").append("*******");
        return sb.toString();
    }

    public static void verifyValidation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
    }
}
