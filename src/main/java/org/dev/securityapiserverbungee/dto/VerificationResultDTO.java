package org.dev.securityapiserverbungee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationResultDTO {

    private final boolean isValid;
    private final int remainingAttempts;
}
