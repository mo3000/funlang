package indi.suiwenbo.funlang.parser;

import java.util.ArrayList;
import java.util.List;

import static indi.suiwenbo.funlang.parser.Util.isPartOfOp;

public class Tokenizer {
    private String lastToken;
    private int pos;
    private int linePos;
    private int line;
    private String text;
    private int linePosBegin;
    private int lineBegin;
    private int prevLineLen;

    public int getPos() {
        return pos;
    }

    public int getLinePos() {
        return linePos;
    }

    public int getLine() {
        return line;
    }

    public Tokenizer() {
        resetPos();
    }

    public Tokenizer(String text) {
        setText(text);
    }


    public void setText(String text) {
        this.text = text;
        resetPos();
    }

    public void resetPos() {
        pos = 0;
        linePos = 0;
        line = 0;
    }

    public boolean notEof() {
        return pos < text.length();
    }

    public Token readOne() {
        skipSpace();
        if (!notEof()) {
            throw new EndOfFileException();
        }
        char c = currentChar();
        int type = charType(c);
        saveLinePos();
        var cache = new StringBuilder();
        while (notEof()) {
            pos++;
            if (isSkipable(c)) {
                if (isNewline(c)) {
                    line++;
                    prevLineLen = linePos;
                    linePos = 0;
                }
                break;
            } else if (c == ';') {
                break;
            }
            if (charType(c) != type) {
                putBack(c);
                break;
            }
            cache.append(c);
            c = currentChar();
        }
        return new Token(cache.toString(), linePosBegin, lineBegin);
    }

    private void saveLinePos() {
        lineBegin = line;
        linePosBegin = linePos;
    }

    private void putBack(char c) {
        pos--;
        if (linePos > 0)
            linePos--;
        else if (linePos == 0) {
            linePos = prevLineLen;
            line--;
        }
    }

    private int charType(char c) {
        if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_') {
            return 1;
        } else if (isPartOfOp(c)) {
            return 2;
        } else if (c == ';') {
            return 3;
        }
        throw new RuntimeException("invalid char c: `" + c + '`');
    }

    private char currentChar() {
        return text.charAt(pos);
    }

    private void skipSpace() {
        char c = currentChar();
        while (notEof() && isSkipable(c) || c == ';') {
            incrPos(c);
            c = currentChar();
        }
    }

    private void incrPos(char c) {
        if (!isNewline(c)) {
            linePos++;
        } else {
            prevLineLen = linePos;
            linePos = 0;
            line++;
        }
        pos++;
    }

    private boolean isSkipable(char c) {
        return c == ' ' || isNewline(c) || c == '\t';
    }

    private boolean isNewline(char c) {
        return c == '\n';
    }
}
