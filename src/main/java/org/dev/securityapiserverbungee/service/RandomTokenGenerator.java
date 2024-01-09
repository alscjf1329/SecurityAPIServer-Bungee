package org.dev.securityapiserverbungee.service;

import java.security.SecureRandom;
import net.md_5.bungee.api.plugin.Plugin;
import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.config.ConfigManager;
import org.dev.securityapiserverbungee.dto.TokenDTO;

public class RandomTokenGenerator {

    public static final String NUMBERS = "0123456789";
    private static final int DEFAULT_LENGTH = 6;
    private static final long DEFAULT_EXPIRATION = 180;
    private static RandomTokenGenerator randomTokenGenerator;
    private final int length;
    private final long expiration;
    private static final long MILLS_PER_SEC = 1000;

    private RandomTokenGenerator() {
        Plugin plugin = SecurityAPIServer_Bungee.getPluginInstance();
        length = ConfigManager.getInstance(plugin)
            .getInt(ConfigManager.TOKENS_LENGTH_OPTION_NAME, DEFAULT_LENGTH);
        expiration = ConfigManager.getInstance(plugin)
            .getLong(ConfigManager.EXPIRATION_OPTION_NAME, DEFAULT_EXPIRATION);
    }

    public static RandomTokenGenerator getInstance() {
        if (randomTokenGenerator == null) {
            randomTokenGenerator = new RandomTokenGenerator();
        }
        return randomTokenGenerator;
    }

    public TokenDTO generate(String elements) {
        StringBuilder result = new StringBuilder();
        SecureRandom rnd = new SecureRandom();

        while (result.length() < length) {
            int index = rnd.nextInt(elements.length());
            result.append(elements.charAt(index));
        }
        return new TokenDTO(result.toString(),
            System.currentTimeMillis() + expiration * MILLS_PER_SEC);
    }
}
