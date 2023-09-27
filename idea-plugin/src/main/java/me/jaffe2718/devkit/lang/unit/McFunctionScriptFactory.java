package me.jaffe2718.devkit.lang.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This is the class to transform the string in *.mcfunction file to a List<String> of commands (MCJE 1.20.2 or later).<br>
 * The commands are not executed here.<br>
 * Normally, each line in the file is a command, but there are some exceptions:<br>
 * 1. The line starts with `# ` is a comment.<br>
 * 2. The text after `# ` in a line is a comment (`# ` must be outside a string).<br>
 * 3. A single backslash `\` as the last non-whitespace character of a line now allows a command to be continued on the next line.
 * */
public class McFunctionScriptFactory {
    private String rawScript = null;     // the whole script string

    public McFunctionScriptFactory(String rawScript) {
        this.rawScript = rawScript;
    }

    public McFunctionScriptFactory() {
        this.rawScript = "";
    }

    public void setRawScript(String rawScript) {
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

    private String removeContinuation(String text) {
        // remove the continuation \ at the end of the line
        return text.replaceAll("\\s*\\\\\\s*$", "");
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

    public String getLastCommand() {
        List<String> lines = this.rawScript.lines().toList().stream().map(this::removeComments).toList();
        StringBuilder currentCommand = new StringBuilder();
        // get the line from the last line backwards to save time
        for (int i = lines.size()-1; i >= 0; i--) {
            String line = lines.get(i);
            String presentLine = i > 0 ? this.removeComments(lines.get(i - 1)) : "";  // the line before the current line
            if (presentLine.endsWith("\\")) {
                currentCommand.insert(0,
                        !this.removeContinuation(line).isBlank()?  // if the line is not blank
                                this.removeContinuation(line) + " " : "");
            } else {
                currentCommand.insert(0, line);
                break;
            }
        }
        return currentCommand.toString().trim();
    }

    public boolean hasMacro() {
        Pattern pattern = Pattern.compile("(^\\$)|(\\n\\$)");
        return pattern.matcher(this.rawScript).find();
    }
}
