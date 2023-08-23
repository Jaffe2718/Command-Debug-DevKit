package me.jaffe2718.devkit.lang.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;

public class McFunctionCompletionContributor extends CompletionContributor {
    public McFunctionCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new McFunctionCompletionProvider());
    }
}
