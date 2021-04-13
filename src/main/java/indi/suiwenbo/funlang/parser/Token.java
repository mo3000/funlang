package indi.suiwenbo.funlang.parser;

public class Token {
    private ValueType valueType;
    private TokenType type;
    private String text;
    private int pos;
    private int line;

    public Token(String text, int pos, int line) {
        this.text = text;
        this.pos = pos;
        this.line = line;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}
