package me.jaffe2718.devkit.lang.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.ExpUiIcons;
import com.intellij.openapi.editor.Editor;
import com.intellij.util.ProcessingContext;
import me.jaffe2718.devkit.action.ConnectCompletionAction;
import me.jaffe2718.devkit.lang.psi.McFunctionTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class McFunctionCompletionProvider extends CompletionProvider<CompletionParameters> {
    @Override
    public void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext context,
                                  @NotNull CompletionResultSet result) {
        // get current textEditor
        AtomicReference<Editor> editor = new AtomicReference<>(parameters.getEditor());
        try {
            Thread.sleep(20);
            AtomicReference<List<String>> completions = new AtomicReference<>(editor.get().getUserData(ConnectCompletionAction.k_completionList));
            result.addAllElements(completions.get().stream().map(completion -> createLookupElement(parameters, completion)).toList());
            completions.get().clear();
        } catch (Exception ignored) {
        }
    }

    private @NotNull LookupElementBuilder createLookupElement(@NotNull CompletionParameters parameters, @NotNull String completion) {
        if (completion.contains(" ")) {  // position is the element
            return LookupElementBuilder.create(completion)
                    .withIcon(ExpUiIcons.Nodes.Parameter)
                    .withInsertHandler((context, item) -> {
                        // get the string of context from beginning to the cursor
                        String text = context.getDocument().getText().substring(0, context.getTailOffset() - completion.length());
                        System.out.println(text);
                        Pattern pos = Pattern.compile("((~?-?\\d+)|~)[ ]?$");
                        for (int i=0; i<3; i++) {
                            Matcher matcher = pos.matcher(text);
                            if (matcher.find()) {
                                // System.out.println(matcher.group());
                                context.getDocument().deleteString(matcher.start(), matcher.end());
                                text = text.substring(0, matcher.start());
                            } else {
                                break;
                            }
                        }
                        //context.getDocument().insertString(context.getTailOffset(), completion);
                    });
        } else if (parameters.getPosition().getNode().getElementType().equals(McFunctionTypes.ELEMENT) &&
                parameters.getPosition().getParent().getNode().getElementType().equals(McFunctionTypes.IDENTIFIER)) {
            // replace the whole identifier, current position is the element, the parent is the identifier(namespace:element)
            LookupElementBuilder builder = LookupElementBuilder.create(completion).withInsertHandler((context, item) ->
                    context.getDocument().replaceString(
                            parameters.getPosition().getParent().getTextOffset(),
                            context.getTailOffset(),
                            completion));
            return completion.contains(":") ? builder.withIcon(ExpUiIcons.Nodes.Interface) : builder.withIcon(ExpUiIcons.Nodes.Parameter);
        } else if (completion.contains(":")) {
            return LookupElementBuilder.create(completion).withIcon(ExpUiIcons.Nodes.Interface);
        } else if (completion.startsWith("@")) {
            return LookupElementBuilder.create(completion).withInsertHandler(
                    (context, item) -> {
                        if (context.getDocument().getText().charAt(context.getTailOffset()-2) == '@') {
                            context.getDocument().deleteString(context.getTailOffset()-2, context.getTailOffset()-1);
                        }
                    }
            ).withIcon(ExpUiIcons.Nodes.Annotation);
        } else if (parameters.getPosition().getNode().getElementType().equals(McFunctionTypes.COMMAND_NAME)) {
            return LookupElementBuilder.create(completion).withIcon(ExpUiIcons.Nodes.Function);
        } else {
            return LookupElementBuilder.create(completion).withIcon(ExpUiIcons.Nodes.Parameter);
        }
    }
}
