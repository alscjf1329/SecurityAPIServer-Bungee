package org.dev.securityapiserverbungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.config.ConfigManager;
import org.dev.securityapiserverbungee.dto.TokenDTO;
import org.dev.securityapiserverbungee.message.Channel;
import org.dev.securityapiserverbungee.message.Messenger;
import org.dev.securityapiserverbungee.security.TokenManager;
import org.dev.securityapiserverbungee.service.RandomTokenGenerator;

public class GenerateTokenCommand extends Command {

    private static final String COMMAND = "token";

    public GenerateTokenCommand() {
        super(COMMAND);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        TokenDTO token = RandomTokenGenerator.getInstance().generate(RandomTokenGenerator.NUMBERS);
        TokenManager.getInstance().addToken(commandSender.getName(), () -> token);

        String expiration = ConfigManager.getInstance(SecurityAPIServer_Bungee.getPluginInstance())
            .getLong(ConfigManager.EXPIRATION_OPTION_NAME) + "";

        Messenger.sendPlayer(Channel.SECURITY_SUBCHANNEL.getName(), commandSender.getName(),
            token.getVerificationCode(), expiration);
    }
}
