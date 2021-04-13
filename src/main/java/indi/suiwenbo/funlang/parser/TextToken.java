package indi.suiwenbo.funlang.parser;

public class TextToken {
    private final String text;

    private final int linePos;
    private final int line;

    public TextToken(String text, int linePos, int line) {
        this.text = text;
        this.linePos = linePos;
        this.line = line;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return String.format("(%s, %d, %d)", text, linePos, line);
    }

    public int getLinePos() {
        return linePos;
    }

    public int getLine() {
        return line;
    }
}
