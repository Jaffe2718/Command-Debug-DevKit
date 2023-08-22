package me.jaffe2718.cmdkit.client;

import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import me.jaffe2718.cmdkit.event.EventHandler;
import me.jaffe2718.cmdkit.mixins.ChatInputSuggestorMixin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class CommandDebugDevKitClient implements ClientModInitializer {

    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        EventHandler.register();
    }

    /**
     * Get the command suggestions for the input
     * In this thread, MinecraftClient.getInstance().setScreen() can not be used,
     * this will cause the game to crash, java.lang.IllegalStateException: Rendersystem called from wrong thread
     * So just set EventHandler.lastCommand = input, and in {@link me.jaffe2718.cmdkit.event.EventHandler} will switch the screen
     * */
    @Contract(pure = true)
    public static @NotNull List<String> getCommandSuggestions(@NotNull String input) {
        EventHandler.lastInput = input;
        List<String> suggestions = new java.util.ArrayList<>();
        for (int i=0 ; i<3 ; i++) {                           // wait for the command suggestions
            if (EventHandler.suggestor != null) {
                CompletableFuture<Suggestions> pendingSuggestions = ((ChatInputSuggestorMixin) EventHandler.suggestor).getPendingSuggestions();
                if (pendingSuggestions != null) {
                    Suggestions s = pendingSuggestions.join();
                    suggestions.addAll(s.getList().stream().map(Suggestion::getText).toList());
                }
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        return suggestions;
    }
}
