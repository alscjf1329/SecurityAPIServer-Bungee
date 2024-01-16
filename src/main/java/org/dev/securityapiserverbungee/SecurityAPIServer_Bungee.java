package org.dev.securityapiserverbungee;

import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import org.dev.securityapiserverbungee.commands.GenerateTokenCommand;
import org.dev.securityapiserverbungee.server.SecurityServer;
import org.dev.securityapiserverbungee.tasks.ClearTokensTask;

public final class SecurityAPIServer_Bungee extends Plugin {

    private static Plugin pluginInstance;
    private ScheduledTask clearTokenSchedule;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pluginInstance = this;
        new Thread(() -> SecurityServer.getInstance(this).start()).start();
        ProxyServer.getInstance().getPluginManager()
            .registerCommand(this, new GenerateTokenCommand());
        clearTokenSchedule = ProxyServer.getInstance().getScheduler().schedule(this,
            new ClearTokensTask(), 0L, 5L, TimeUnit.MINUTES);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SecurityServer.getInstance(this).stop();
        if (clearTokenSchedule != null) {
            clearTokenSchedule.cancel();
        }
    }

    public static Plugin getPluginInstance() {
        return pluginInstance;
    }
}
