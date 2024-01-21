package org.dev.securityapiserverbungee.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dev.securityapiserverbungee.dto.VerificationResultDTO;
import org.dev.securityapiserverbungee.policies.TokenSupplier;

public class TokenManager {

    private static final int ZERO = 0;
    private static TokenManager tokenManager;
    // nickname to token, expiryTime
    private static Map<String, Token> authTokenMap;

    private TokenManager() {
        authTokenMap = new ConcurrentHashMap<>();
    }

    public static TokenManager getInstance() {
        if (tokenManager == null) {
            tokenManager = new TokenManager();
        }
        return tokenManager;
    }


    public VerificationResultDTO authenticate(String nickname, String verificationCode) {
        Token tokenInfo = authTokenMap.get(nickname);
        int attemptCountRemaining = ZERO;
        if (tokenInfo != null) {
            attemptCountRemaining = tokenInfo.deductAuthenticationAttemptCountRemaining();
        }
        boolean isValid = validate(nickname, verificationCode);
        if (isValid) {
            authTokenMap.remove(nickname);
        }
        return new VerificationResultDTO(isValid, attemptCountRemaining);
    }

    public boolean validate(String nickname, String verificationCode) {
        Token tokenInfo = authTokenMap.get(nickname);
        if (tokenInfo == null) {
            return false;
        }
        return tokenInfo.isValid(verificationCode);
    }

    public Token addToken(String nickname, TokenSupplier tokenSupplier) {
        Token token = tokenSupplier.get();
        authTokenMap.put(nickname, token);
        return token;
    }

    public void clear() {
        if (authTokenMap == null) {
            return;
        }
        authTokenMap.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}