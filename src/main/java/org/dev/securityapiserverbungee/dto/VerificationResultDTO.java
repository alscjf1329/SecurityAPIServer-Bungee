package org.dev.securityapiserverbungee.dto;

import lombok.Getter;

@Getter
public class VerificationResultDTO {

    private final boolean isValid;
    private final int remainingAttempts;

    public VerificationResultDTO(boolean isValid, int authenticationAttemptCountRemaining) {
        this.isValid = isValid;
        this.remainingAttempts = authenticationAttemptCountRemaining;
    }
}
