package indi.suiwenbo.funlang.parser;

public class Token {
    private final String text;

    private final int linePos;
    private final int line;

    public Token(String text, int linePos, int line) {
        this.text = text;
        this.linePos = linePos;
        this.line = line;
    }

    public String getText() {
        return text;
    }

    public int getLinePos() {
        return linePos;
    }

    public int getLine() {
        return line;
    }
}
