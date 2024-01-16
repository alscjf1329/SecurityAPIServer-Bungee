package org.dev.securityapiserverbungee.server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.dev.securityapiserverbungee.SecurityAPIServer_Bungee;
import org.dev.securityapiserverbungee.config.ConfigManager;
import org.dev.securityapiserverbungee.views.ConfigOutView;

public class SecurityServer {

    private Plugin plugin;

    private static volatile SecurityServer instance = null;
    private static final int DEFAULT_PORT = 15000;
    private static final boolean DEFAULT_LOG_FLAG = true;
    private HttpServer server;

    private SecurityServer(Plugin plugin) {
        this.plugin = plugin;
    }

    public static SecurityServer getInstance(Plugin plugin) {
        if (instance == null) {
            synchronized (SecurityServer.class) {
                if (instance == null) {
                    instance = new SecurityServer(plugin);
                }
            }
        }
        return instance;
    }

    public void start() {
        Configuration config = ConfigManager.getInstance(
            SecurityAPIServer_Bungee.getPluginInstance());

        int port = config.getInt(ConfigManager.PORT_OPTION_NAME, DEFAULT_PORT);

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            ConfigOutView.SERVER_START_FORMAT.print(port);
            ConfigOutView.API_PATH_LIST_TITLE.print();

            Arrays.stream(EndPoints.values()).forEach(api -> {
                String apiPath = config.getString(
                    String.format(ConfigManager.PATH_OPTION_FORMAT, api.getName()),
                    api.getDefaultPath());
                ConfigOutView.API_PATH_FORMAT.print(api.getName(), apiPath);
                server.createContext(apiPath, api.getHandler());
            });
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }


}