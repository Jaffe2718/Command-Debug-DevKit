package me.jaffe2718.cmdkit.mixins;

import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Get the chatInputSuggestor and chatField of ChatScreen.<br>
 * {@link net.minecraft.client.gui.screen.ChatScreen}<br>
 *
 * Usage:<br>
 * <br>
 * {@code ChatScreen chatScreen = ...;}<br>
 * <br>
 * {@code ChatInputSuggestor chatInputSuggestor = ((ChatScreenMixin) chatScreen).getChatInputSuggestor();}<br>
 * <br>
 * {@code TextFieldWidget chatField = ((ChatScreenMixin) chatScreen).getChatField();}<br>
 * */
@Mixin(ChatScreen.class)
public interface ChatScreenMixin {

    /**
     * Get the chatInputSuggestor field of ChatScreen
     * @return the chatInputSuggestor field of ChatScreen
     * */
    @Accessor
    ChatInputSuggestor getChatInputSuggestor();

    /**
     * Get the chatField field of ChatScreen
     * @return the chatField field of ChatScreen
     * */
    @Accessor
    TextFieldWidget getChatField();

}