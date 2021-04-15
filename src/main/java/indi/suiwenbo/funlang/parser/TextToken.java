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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextToken textToken = (TextToken) o;

        if (linePos != textToken.linePos) return false;
        if (line != textToken.line) return false;
        return text.equals(textToken.text);
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + linePos;
        result = 31 * result + line;
        return result;
    }
}
