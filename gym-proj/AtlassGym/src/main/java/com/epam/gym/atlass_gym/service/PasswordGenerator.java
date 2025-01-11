package com.epam.gym.atlass_gym.service;

public class PasswordGenerator {
    static public String generatePassword() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = (int) (base.length() * Math.random());
            sb.append(base
                    .charAt(index));
        }
        return sb.toString();
    }
}
