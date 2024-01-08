package org.dev.securityapiserverbungee.dto;

public class TokenDTO {
    private final String verificationCode;
    private final long expiryTime;

    public TokenDTO(String verificationCode, long expiration) {
        this.verificationCode = verificationCode;
        this.expiryTime = System.currentTimeMillis() + expiration;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public long getExpiryTime() {
        return expiryTime;
    }
}
