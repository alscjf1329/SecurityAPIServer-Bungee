package org.dev.securityapiserverbungee.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dev.securityapiserverbungee.dto.TokenDTO;
import org.dev.securityapiserverbungee.policies.TokenSupplier;

public class TokenManager {

    private static TokenManager tokenManager;
    // nickname to token, expiration
    private static Map<String, TokenDTO> authTokenMap;

    private TokenManager() {
        authTokenMap = new ConcurrentHashMap<>();
    }

    public boolean isValid(String nickname, String verificationCode) {
        TokenDTO tokenInfo = authTokenMap.get(nickname);
        if (tokenInfo == null) {
            return false;
        }
        if (System.currentTimeMillis() > tokenInfo.getExpiryTime()) {
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

    public static TokenManager getInstance() {
        if (tokenManager == null) {
            tokenManager = new TokenManager();
        }
        return tokenManager;
    }
}