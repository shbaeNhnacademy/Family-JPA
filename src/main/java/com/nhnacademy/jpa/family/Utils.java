package com.nhnacademy.jpa.family;

public class Utils {
    public static String getMarkedNumber(String registrationNumber) {
        StringBuilder sb = new StringBuilder();
        String[] split = registrationNumber.split("-");
        sb.append(split[0]).append("-").append("*******");
        return sb.toString();
    }
}
