package org.dev.securityapiserverbungee.dto;

public class VerificationResultDTO {

    private final boolean isValid;
    private final int authenticationAttemptCountRemaining;

    public VerificationResultDTO(boolean isValid, int authenticationAttemptCountRemaining) {
        this.isValid = isValid;
        this.authenticationAttemptCountRemaining = authenticationAttemptCountRemaining;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public int getAuthenticationAttemptCountRemaining() {
        return authenticationAttemptCountRemaining;
    }

}
