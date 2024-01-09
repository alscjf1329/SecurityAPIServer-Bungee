package org.dev.securityapiserverbungee.dto;

import net.md_5.bungee.api.plugin.Plugin;
import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.config.ConfigManager;
import org.dev.securityapiserverbungee.policies.VerificationCodeSupplier;

public class TokenDTO {

    public static final long DEFAULT_EXPIRATION = 180;
    public static final int MILLS_PER_SEC = 1000;
    private final String verificationCode;
    private final long expiryTime;

    public TokenDTO(VerificationCodeSupplier verificationCodeSupplier) {
        Plugin plugin = SecurityAPIServer_Bungee.getPluginInstance();
        long expiration = ConfigManager.getInstance(plugin)
            .getLong(ConfigManager.EXPIRATION_OPTION_NAME, DEFAULT_EXPIRATION) * MILLS_PER_SEC;
        this.verificationCode = verificationCodeSupplier.get();
        this.expiryTime = System.currentTimeMillis() + expiration;
    }

    public TokenDTO(VerificationCodeSupplier verificationCodeSupplier, long expiration) {
        this.verificationCode = verificationCodeSupplier.get();
        this.expiryTime = System.currentTimeMillis() + expiration;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public long getExpiryTime() {
        return expiryTime;
    }
}
