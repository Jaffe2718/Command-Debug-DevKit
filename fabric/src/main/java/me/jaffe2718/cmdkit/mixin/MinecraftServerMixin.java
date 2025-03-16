package me.jaffe2718.cmdkit.mixin;

import me.jaffe2718.cmdkit.event.EventHandler;
import net.minecraft.network.message.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "logChatMessage", at = @At("HEAD"))
    private void syncMessageToQueue(Text message, MessageType.@NotNull Parameters params, String prefix, CallbackInfo ci) {
        if (prefix != null) {
            EventHandler.syncMessageQueue.add(String.format("<%s> %s", prefix, message.getString()));
        } else {
            EventHandler.syncMessageQueue.add(message.getString());
        }
    }

    @Inject(method = "sendMessage", at = @At("HEAD"))
    private void syncMessageToQueue(@NotNull Text message, CallbackInfo ci) {
        EventHandler.syncMessageQueue.add(message.getString());
    }
}
