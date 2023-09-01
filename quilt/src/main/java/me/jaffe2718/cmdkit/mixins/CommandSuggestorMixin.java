package me.jaffe2718.cmdkit.mixins;

import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.screen.CommandSuggestor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.CompletableFuture;

/**
 * This mixin interface is used to get the pendingSuggestions field of ChatInputSuggestor.
 * {@link net.minecraft.client.gui.screen.CommandSuggestor}<br>
 * {@link CommandSuggestorMixin#getPendingSuggestions()} <br>
 * <br>
 * Usage:<br>
 * <br>
 * {@code ChatInputSuggestor suggestor = ...;}<br>
 * {@code CompletableFuture<Suggestions> pendingSuggestions = ((CommandSuggestorMixin) suggestor).getPendingSuggestions();}
 * */
@Mixin(CommandSuggestor.class)
public interface CommandSuggestorMixin {

    /**
     * Get the pendingSuggestions field of {@link net.minecraft.client.gui.screen.CommandSuggestor}
     * @return the pendingSuggestions field of {@link net.minecraft.client.gui.screen.CommandSuggestor}
     * */
    @Accessor
    CompletableFuture<Suggestions> getPendingSuggestions();
}
