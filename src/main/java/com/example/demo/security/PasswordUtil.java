package com.example.demo.security;

import java.security.MessageDigest;
import java.util.Base64;

public class PasswordUtil {

    private PasswordUtil() {}

    public static String hash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(digest);
    }
}
