package me.jaffe2718.sckinj.client;

import me.jaffe2718.sckinj.event.EventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.net.Socket;

@Environment(EnvType.CLIENT)
public class SocketInjectorClient implements ClientModInitializer {

    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        EventHandler.register();
    }
}
