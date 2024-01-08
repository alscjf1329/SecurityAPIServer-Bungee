package org.dev.securityapiserverbungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.dev.securityapiserverbungee.commands.GenerateTokenCommand;
import org.dev.securityapiserverbungee.server.SecurityServer;

public final class SecurityAPIServer_Bungee extends Plugin {

    private static Plugin pluginInstance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pluginInstance = this;
        new Thread(() -> SecurityServer.getInstance(this).start()).start();
        ProxyServer.getInstance().getPluginManager()
            .registerCommand(this, new GenerateTokenCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SecurityServer.getInstance(this).stop();
    }

    public static Plugin getPluginInstance() {
        return pluginInstance;
    }
}
