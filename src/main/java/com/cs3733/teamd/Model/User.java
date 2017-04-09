package com.cs3733.teamd.Model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by sdmichelini on 4/1/17.
 */
public class User {
    private String username;
    private List<String> roles;

    public User(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public void addRole(String role) {
        roles.add(role);
    }

    /**
     * Calculates the Hash of an input string
     * @param input - String to be hashed
     * @return - MD5 hash of the string
     */
    public static String calculateHash(String input) throws Exception {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(input.getBytes(),0,input.length());

        return toHexString(m.digest());
    }

    private static String toHexString(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("byte array must not be null");
        }
        StringBuffer hex = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            hex.append(Character.forDigit((bytes[i] & 0XF0) >> 4, 16));
            hex.append(Character.forDigit((bytes[i] & 0X0F), 16));
        }
        return hex.toString();
    }
}
