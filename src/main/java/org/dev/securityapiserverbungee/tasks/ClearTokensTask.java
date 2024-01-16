package org.dev.securityapiserverbungee.tasks;

import org.dev.securityapiserverbungee.security.TokenManager;
import org.dev.securityapiserverbungee.views.ConfigOutView;

public class ClearTokensTask implements Runnable {

    @Override
    public void run() {
        TokenManager.getInstance().clear();
        ConfigOutView.CLEAR_MESSAGE_FORMAT.print(System.currentTimeMillis());
    }
}
