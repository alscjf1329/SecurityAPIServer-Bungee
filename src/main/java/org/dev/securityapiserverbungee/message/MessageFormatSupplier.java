package org.dev.securityapiserverbungee.message;

import com.google.common.io.ByteArrayDataOutput;

public interface MessageFormatSupplier {
    ByteArrayDataOutput get(String channel, String message);
}
