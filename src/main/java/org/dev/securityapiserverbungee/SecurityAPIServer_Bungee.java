package org.dev.securityapiserverbungee;

import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import org.dev.securityapiserverbungee.commands.GenerateTokenCommand;
import org.dev.securityapiserverbungee.security.TokenManager;
import org.dev.securityapiserverbungee.server.SecurityServer;

public final class SecurityAPIServer_Bungee extends Plugin {

    private static Plugin pluginInstance;
    private ScheduledTask clearTokenManagerTask;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pluginInstance = this;
        new Thread(() -> SecurityServer.getInstance(this).start()).start();
        ProxyServer.getInstance().getPluginManager()
            .registerCommand(this, new GenerateTokenCommand());
        clearTokenManagerTask = ProxyServer.getInstance().getScheduler().schedule(this,
            () -> {
                TokenManager.getInstance().clear();
                System.out.println("clear!!");
            }
            , 0L, 5L, TimeUnit.MINUTES);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SecurityServer.getInstance(this).stop();
        if (clearTokenManagerTask != null) {
            clearTokenManagerTask.cancel();
        }
    }

    public static Plugin getPluginInstance() {
        return pluginInstance;
    }
}
