package indi.suiwenbo.funlang.parser;

public class ParseError extends RuntimeException {
    private final int pos;
    private final int line;

    public ParseError(String msg, int pos, int line) {
        super(msg);
        this.pos = pos;
        this.line = line;
    }

    public int getPos() {
        return pos;
    }

    public int getLine() {
        return line;
    }

    public String toString() {
        return getMessage() + String.format(" at line %d:%d", line, pos);
    }
}
