package org.dev.securityapiserverbungee.message;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.util.Arrays;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class Messenger {


    private Messenger() {

    }

    public static void sendAll(String channel, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF(channel);
        out.writeUTF(message);

        for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
            server.sendData(Channel.BUNGEECORD_CHANNEL, out.toByteArray());
        }
    }

    public static void sendPlayer(String channel, String nickname, String... messages) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ForwardToPlayer");
        out.writeUTF(nickname);
        out.writeUTF(channel);
        Arrays.stream(messages).forEach(out::writeUTF);

        ProxyServer.getInstance().getPlayer(nickname).getServer()
            .sendData(Channel.BUNGEECORD_CHANNEL, out.toByteArray());
    }
}

