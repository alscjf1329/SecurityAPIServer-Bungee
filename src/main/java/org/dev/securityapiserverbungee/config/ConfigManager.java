package org.dev.securityapiserverbungee.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.dev.securityapiserverbungee.server.EndPoints;

public class ConfigManager {

    public static final String CONFIG_FILE_NAME = "config.yml";
    public static final String PATH_OPTION_FORMAT = "server.path.%s";
    public static final String PORT_OPTION_NAME = "server.port";
    public static final String LOG_OPTION = "server.log_flag";
    public static final String TOKENS_LENGTH_OPTION_NAME = "token.length";
    public static final String EXPIRATION_OPTION_NAME = "token.expiration(sec)";
    private static ConfigManager configManager;
    private static Plugin plugin;
    private static Configuration configuration;

    private ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        try {
            configuration = readConfigFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration getInstance(Plugin plugin) {
        if (configManager == null) {
            configManager = new ConfigManager(plugin);
        }
        return configuration;
    }

    private static Configuration readConfigFile() throws IOException {
        File configFile = new File(plugin.getDataFolder(), CONFIG_FILE_NAME);
        if (configFile.exists()) {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        }
        plugin.getDataFolder().mkdirs();

        try (InputStream in = plugin.getResourceAsStream("config.yml")) {
            if (in == null) {
                throw new IOException("Resource file config.yml not found");
            }
            Files.copy(in, configFile.toPath());
        }

        Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class)
            .load(configFile);
        Arrays.stream(EndPoints.values())
            .forEach(api -> config.set(String.format(PATH_OPTION_FORMAT, api.getName()),
                api.getDefaultPath()));
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);

        return config;
    }
}
