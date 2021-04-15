package indi.suiwenbo.funlang.parser;

import static indi.suiwenbo.funlang.parser.Util.isPartOfOp;
import static indi.suiwenbo.funlang.parser.Util.operators;

public class Tokenizer {
    private String lastToken;
    private int pos;
    private int linePos;
    private int line;
    private String text;
    private int linePosBegin;
    private int lineBegin;
    private int prevLineLen;

    private final OpTree treeRoot = operators;
    private OpTree treeNode;

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

    private enum CharType {
        Word, Op, SemiColon;
    }

    public TextToken readOne() {
        skipSpace();
        if (!notEof()) {
            throw new EndOfFileException();
        }
        char c = currentChar();
        CharType type = charType(c);
        StringBuilder cache = null;
        saveLinePos();
        int dotCount = 0;
        if (type == CharType.Word) {
            cache = new StringBuilder();
        } else if (type == CharType.SemiColon) {
            incrPos(c);
            return new TextToken(";", linePosBegin, lineBegin);
        } else if (type == CharType.Op) {
            resetTree();
        }
        boolean isDigit = Character.isDigit(c);
        while (notEof()) {
            if (isSkipable(c)) {
                if (isNewline(c)) {
                    line++;
                    prevLineLen = linePos;
                    linePos = 0;
                }
                break;
            } else if (c == ';') {
                break;
            } else if (c == '.') {
                if (!isDigit) {
                    break;
                }
                dotCount++;
                if (dotCount > 1) {
                    throw new ParseError("unexpected `.`", linePos, line);
                }
            } else if (charType(c) != type) {
                break;
            }
            if (cache != null) {
                cache.append(c);
            } else {
                if (!treeNode.has(c)) {
                    throw new ParseError("unexpected char `" + c + '`', linePos, line);
                }
                treeNode = treeNode.get(c);
                if (!treeNode.hasNext()) {
                    //c is acceptable
                    incrPos(c);
                    break;
                }
            }
            incrPos(c);
            c = currentChar();
        }
        if (type == CharType.Word) {
            return new TextToken(cache.toString(), linePosBegin, lineBegin);
        } else {
            assert type == CharType.Op;
            if (!treeNode.acceptable()) {
                throw new ParseError(
                    "unknown operator near: " + text.substring(linePos > 5 ? linePos - 5 : 0, linePos + 1),
                    linePos, line);
            }
            String text = treeNode.accept();
            return new TextToken(text, linePosBegin, lineBegin);
        }
    }

    private void resetTree() {
        treeNode = treeRoot;
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

    private CharType charType(char c) {
        if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_') {
            return CharType.Word;
        } else if (isPartOfOp(c)) {
            return CharType.Op;
        } else if (c == ';') {
            return CharType.SemiColon;
        }
        throw new RuntimeException("invalid char c: `" + c + '`');
    }

    private char currentChar() {
        return text.charAt(pos);
    }

    private void skipSpace() {
        char c = currentChar();
        while (notEof() && isSkipable(c)) {
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
