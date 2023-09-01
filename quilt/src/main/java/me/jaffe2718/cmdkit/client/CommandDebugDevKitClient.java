package me.jaffe2718.cmdkit.client;

import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import me.jaffe2718.cmdkit.event.EventHandler;
import me.jaffe2718.cmdkit.mixins.CommandSuggestorMixin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class CommandDebugDevKitClient implements ClientModInitializer {


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
                CompletableFuture<Suggestions> pendingSuggestions = ((CommandSuggestorMixin) EventHandler.suggestor).getPendingSuggestions();
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

    /**
     * Runs the mod initializer on the client environment.
     *
     * @param mod the mod which is initialized
     */
    @Override
    public void onInitializeClient(ModContainer mod) {
        EventHandler.register();
    }
}
