package org.dev.securityapiserverbungee.dto;

public class VerificationResultDTO {

    private final boolean isValid;
    private final int remainingAttempts;

    public VerificationResultDTO(boolean isValid, int authenticationAttemptCountRemaining) {
        this.isValid = isValid;
        this.remainingAttempts = authenticationAttemptCountRemaining;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

}
