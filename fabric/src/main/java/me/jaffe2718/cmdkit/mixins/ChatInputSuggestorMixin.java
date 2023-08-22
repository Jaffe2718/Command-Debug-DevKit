package me.jaffe2718.cmdkit.mixins;

import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.CompletableFuture;

/**
 * This mixin interface is used to get the pendingSuggestions field of ChatInputSuggestor.
 * {@link net.minecraft.client.gui.screen.ChatInputSuggestor}<br>
 * {@link ChatInputSuggestorMixin#getPendingSuggestions()} <br>
 * <br>
 * Usage:<br>
 * <br>
 * {@code ChatInputSuggestor suggestor = ...;}<br>
 * {@code CompletableFuture<Suggestions> pendingSuggestions = ((ChatInputSuggestorMixin) suggestor).getPendingSuggestions();}
 * */
@Mixin(ChatInputSuggestor.class)
public interface ChatInputSuggestorMixin {

    /**
     * Get the pendingSuggestions field of {@link net.minecraft.client.gui.screen.ChatInputSuggestor}
     * @return the pendingSuggestions field of {@link net.minecraft.client.gui.screen.ChatInputSuggestor}
     * */
    @Accessor
    CompletableFuture<Suggestions> getPendingSuggestions();
}
