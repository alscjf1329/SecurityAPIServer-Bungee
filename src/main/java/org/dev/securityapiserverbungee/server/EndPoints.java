package org.dev.securityapiserverbungee.server;

import com.sun.net.httpserver.HttpHandler;
import org.dev.securityapiserverbungee.handler.PlayerTokenHandler;


public enum EndPoints {
    PLAYER_TOKEN("generateUserTokenPath", "/authenticate/token", new PlayerTokenHandler());
    private final String name;
    private final String defaultPath;
    private final HttpHandler handler;

    EndPoints(String name, String defaultPath, HttpHandler handler) {
        this.name = name;
        this.defaultPath = defaultPath;
        this.handler = handler;
    }

    public String getName() {
        return name;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public HttpHandler getHandler() {
        return handler;
    }
}
