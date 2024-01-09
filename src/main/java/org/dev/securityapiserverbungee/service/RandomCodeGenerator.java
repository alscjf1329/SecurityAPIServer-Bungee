package org.dev.securityapiserverbungee.service;

import java.security.SecureRandom;

public class RandomCodeGenerator {

    public static final String NUMBERS = "0123456789";
    private static final int DEFAULT_LENGTH = 6;
    private static final long DEFAULT_EXPIRATION = 180;
    private static RandomCodeGenerator randomTokenGenerator;

    private RandomCodeGenerator() {
    }

    public static RandomCodeGenerator getInstance() {
        if (randomTokenGenerator == null) {
            randomTokenGenerator = new RandomCodeGenerator();
        }
        return randomTokenGenerator;
    }

    public String generate(long length, String elements) {
        StringBuilder result = new StringBuilder();
        SecureRandom rnd = new SecureRandom();

        while (result.length() < length) {
            int index = rnd.nextInt(elements.length());
            result.append(elements.charAt(index));
        }
        return result.toString();
    }
}
