package indi.suiwenbo.funlang.parser;

public class Token {
    private final TokenType type;

    private final String text;


    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }
}
