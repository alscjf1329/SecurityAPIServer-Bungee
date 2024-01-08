package org.dev.securityapiserverbungee.message;

public enum Channel {
    SECURITY_SUBCHANNEL("Security");
    public static final String BUNGEECORD_CHANNEL = "BungeeCord";
    private final String name;

    Channel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
