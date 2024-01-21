package org.dev.securityapiserverbungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.server.SecurityServer;

public class ReloadCommand extends Command {

    private static final String COMMAND = "reload SecurityServer";

    public ReloadCommand() {
        super(COMMAND);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            return;
        }
        SecurityServer.getInstance(SecurityAPIServer_Bungee.getPluginInstance()).stop();
        SecurityServer.getInstance(SecurityAPIServer_Bungee.getPluginInstance()).start();
    }
}
