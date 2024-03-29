package org.dev.securityapiserverbungee.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.config.ConfigManager;
import org.dev.securityapiserverbungee.policies.VerificationCodeSupplier;

@Getter
@AllArgsConstructor
public class Token {

    public static final int DEFAULT_AUTHENTICATION_ATTEMPT_COUNT_REMAINING = 5;
    public static final long DEFAULT_EXPIRATION = 180;
    public static final int MILLS_PER_SEC = 1000;
    private static final int ZERO = 0;
    private final String verificationCode;
    private int authenticationAttemptCountRemaining;
    private final long expiryTime;

    public Token(VerificationCodeSupplier verificationCodeSupplier) {
        Plugin plugin = SecurityAPIServer_Bungee.getPluginInstance();
        Configuration configuration = ConfigManager.getInstance(plugin);
        long expiration =
            configuration.getLong(ConfigManager.EXPIRATION_OPTION_NAME, DEFAULT_EXPIRATION)
                * MILLS_PER_SEC;

        this.authenticationAttemptCountRemaining = configuration.getInt(
            ConfigManager.TOKEN_MAX_AUTHENTICATION_ATTEMPT_COUNT_OPTION_NAME,
            DEFAULT_AUTHENTICATION_ATTEMPT_COUNT_REMAINING);
        this.verificationCode = verificationCodeSupplier.get();
        this.expiryTime = System.currentTimeMillis() + expiration;
    }

    public boolean isValid(String verificationCode) {
        if (!(this.verificationCode.equals(verificationCode))) {
            return false;
        }
        if (isExpired()) {
            return false;
        }
        return true;
    }

    public int deductAuthenticationAttemptCountRemaining() {
        if (authenticationAttemptCountRemaining <= ZERO) {
            return ZERO;
        }
        return --this.authenticationAttemptCountRemaining;
    }

    public boolean isExpired() {
        if (this.authenticationAttemptCountRemaining <= ZERO) {
            return true;
        }
        if (System.currentTimeMillis() > expiryTime) {
            return true;
        }
        return false;
    }
}
