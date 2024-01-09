package org.dev.securityapiserverbungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.config.ConfigManager;
import org.dev.securityapiserverbungee.dto.TokenDTO;
import org.dev.securityapiserverbungee.message.Channel;
import org.dev.securityapiserverbungee.message.Messenger;
import org.dev.securityapiserverbungee.security.TokenManager;
import org.dev.securityapiserverbungee.service.RandomCodeGenerator;

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
        Plugin plugin = SecurityAPIServer_Bungee.getPluginInstance();
        Configuration configManager = ConfigManager.getInstance(plugin);
        int length = configManager.getInt(ConfigManager.TOKENS_LENGTH_OPTION_NAME);
        String code = RandomCodeGenerator.getInstance()
            .generate(length, RandomCodeGenerator.NUMBERS);

        TokenDTO token = TokenManager.getInstance()
            .addToken(commandSender.getName(), () -> new TokenDTO(() -> code));

        String expiration =
            String.valueOf(configManager.getLong(ConfigManager.EXPIRATION_OPTION_NAME,
                TokenDTO.DEFAULT_EXPIRATION));

        Messenger.sendPlayer(Channel.SECURITY_SUBCHANNEL.getName(), commandSender.getName(),
            token.getVerificationCode(), expiration);
    }
}
