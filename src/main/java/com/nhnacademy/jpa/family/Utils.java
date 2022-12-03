package com.nhnacademy.jpa.family;

import com.nhnacademy.jpa.family.exception.ValidationFailedException;
import org.springframework.validation.BindingResult;

public class Utils {
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
