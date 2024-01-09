package org.dev.securityapiserverbungee.dto;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.config.ConfigManager;
import org.dev.securityapiserverbungee.policies.VerificationCodeSupplier;

public class TokenDTO {

    public static final int DEFAULT_AUTHENTICATION_ATTEMPT_COUNT_REMAINING = 5;
    public static final long DEFAULT_EXPIRATION = 180;
    public static final int MILLS_PER_SEC = 1000;
    private final String verificationCode;
    private int authenticationAttemptCountRemaining;
    private final long expiryTime;

    public TokenDTO(VerificationCodeSupplier verificationCodeSupplier) {
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

    public TokenDTO(VerificationCodeSupplier verificationCodeSupplier,
        int authenticationAttemptCountRemaining, long expiration) {
        this.verificationCode = verificationCodeSupplier.get();
        this.authenticationAttemptCountRemaining = authenticationAttemptCountRemaining;
        this.expiryTime = System.currentTimeMillis() + expiration;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void deductAuthenticationAttemptCountRemaining() {
        this.authenticationAttemptCountRemaining--;
    }
}
