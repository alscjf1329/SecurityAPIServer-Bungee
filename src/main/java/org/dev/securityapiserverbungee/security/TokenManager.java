package org.dev.securityapiserverbungee.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dev.securityapiserverbungee.dto.TokenDTO;
import org.dev.securityapiserverbungee.policies.TokenSupplier;

public class TokenManager {

    private static TokenManager tokenManager;
    // nickname to token, expiryTime
    private static Map<String, TokenDTO> authTokenMap;

    private TokenManager() {
        authTokenMap = new ConcurrentHashMap<>();
    }

    public static TokenManager getInstance() {
        if (tokenManager == null) {
            tokenManager = new TokenManager();
        }
        return tokenManager;
    }

    public boolean isValid(String nickname, String verificationCode) {
        TokenDTO tokenInfo = authTokenMap.get(nickname);
        if (tokenInfo == null) {
            return false;
        }
        if (isExpired(tokenInfo.getExpiryTime())) {
            return false;
        }
        if (!verificationCode.equals(tokenInfo.getVerificationCode())) {
            return false;
        }
        return true;
    }

    public void addToken(String nickname, TokenSupplier tokenSupplier) {
        authTokenMap.put(nickname, tokenSupplier.get());
    }

    public void clear() {
        if (authTokenMap == null) {
            return;
        }
        authTokenMap.entrySet().removeIf(entry -> isExpired(entry.getValue().getExpiryTime()));
    }

    private boolean isExpired(long expiryTime) {
        return System.currentTimeMillis() > expiryTime;
    }
}