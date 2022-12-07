package com.nhnacademy.jpa.family.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class PasswordUtils {
    private static final int DEFAULT_ITERATIONS = 1024;

    private PasswordUtils() {
        throw new IllegalStateException("Utility class");
    }


    public static String encode(String rawPassword, byte[] salt) {
        return encode(rawPassword, salt, DEFAULT_ITERATIONS);
    }

    public static String encode(String rawPassword) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] input = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(input);
    }
    public static String encode(String rawPassword, byte[] salt, int iterations) {
        byte[] digest = null;
        try {
            digest = sha256(rawPassword, salt, iterations);
        } catch (NoSuchAlgorithmException ex) {
            log.error("", ex);
        }

        return bytesToHex(digest);
    }

    private static byte[] sha256(String rawPassword, byte[] salt, int iterations) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);

        byte[] input = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            input = digest.digest(input);
        }

        return input;
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
