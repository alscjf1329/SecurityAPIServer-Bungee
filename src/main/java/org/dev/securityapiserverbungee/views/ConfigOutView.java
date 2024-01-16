package org.dev.securityapiserverbungee.views;

import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.config.ConfigManager;

public enum ConfigOutView {
    SERVER_START_FORMAT("Starting Public API Server on port %s"),
    API_PATH_LIST_TITLE("API Path List"),
    API_PATH_FORMAT("  %s : %s"),
    CLEAR_MESSAGE_FORMAT("clear at %d");
    public static final String NEW_LINE = System.lineSeparator();
    private final String messageFormat;

    ConfigOutView(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public void print(Object... args) {
        boolean logFlag = ConfigManager.getInstance(SecurityAPIServer_Bungee.getPluginInstance())
            .getBoolean(ConfigManager.LOG_OPTION);
        if (logFlag) {
            System.out.printf(messageFormat + NEW_LINE, args);
        }
    }
}
