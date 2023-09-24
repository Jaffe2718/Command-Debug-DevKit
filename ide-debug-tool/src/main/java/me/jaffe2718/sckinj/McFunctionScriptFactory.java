package me.jaffe2718.sckinj;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class to transform the string in *.mcfunction file to a List<String> of commands (MCJE 1.20.2 or later).<br>
 * The commands are not executed here.<br>
 * Normally, each line in the file is a command, but there are some exceptions:<br>
 * 1. The line starts with `# ` is a comment.<br>
 * 2. The text after `# ` in a line is a comment (`# ` must be outside a string).<br>
 * 3. A single backslash `\` as the last non-whitespace character of a line now allows a command to be continued on the next line.
 * */
public class McFunctionScriptFactory {
    private final String rawScript;     // the whole script string

    public McFunctionScriptFactory(String rawScript) {
        this.rawScript = rawScript;
    }

    private String removeComments(String text) {
        // remove the comments #... and the spaces before it
        // but strings are not comments like "...# ...", there must be a space before the #
        boolean inString = false;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (inString) {
                if (c == '"') {             // end of string
                    inString = false;
                }
                result.append(c);
            }
            else {          // not in string
                if (c == '"') {             // start of string
                    inString = true;
                } else if (c == '#') {      // start of comment
                    if (i+1<text.length() && text.charAt(i+1) == ' ') {
                        break;
                    }
                }
                result.append(c);
            }
        }
        return result.toString().trim();
    }

    public List<String> getCommands() {
        List<String> lines = this.rawScript.lines().toList().stream().map(this::removeComments).toList();
        List<String> commands = new ArrayList<>();
        StringBuilder currentCommand = new StringBuilder();
        for (String line: lines) {
            if (line.isBlank()) continue;    // ignore blank lines
            if (line.endsWith("\\")) {
                // remove the `\` at the end of the line and remove the multiple spaces at the end then add one space
                String part = line.replaceAll("\\s*\\\\\\s*$", " ");
                if (part.isBlank()) continue;  // ignore blank lines
                currentCommand.append(part);
            } else {
                currentCommand.append(line);
                commands.add(currentCommand.toString());
                currentCommand = new StringBuilder();
            }
        }
        return commands;
    }
}
