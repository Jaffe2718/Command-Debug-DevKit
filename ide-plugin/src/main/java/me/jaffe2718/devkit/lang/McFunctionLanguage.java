package me.jaffe2718.devkit.lang;

import com.intellij.lang.Language;

public class McFunctionLanguage extends Language {
    public static final McFunctionLanguage INSTANCE = new McFunctionLanguage();

    protected McFunctionLanguage() {
        super("McFunction");
    }
}
