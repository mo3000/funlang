package indi.suiwenbo.funlang.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Lexer {

    public final static Pattern IntegerPat = Pattern.compile("^[0-9]+$");
    public final static Pattern FloatPat = Pattern.compile("^[0-9]+\\.[0-9]+$");

    private final List<TextToken> rawTokens;

    public Lexer() {
        rawTokens = new ArrayList<>();
    }

    public void recognize(TextToken token) {
        String text = token.getText();
        if (Character.isDigit(text.charAt(0))) {
            if (IntegerPat.matcher(text).find()) {
                System.out.println(text + "is int");
            } else {
                throw new ParseError("invalid text: " + text, token.getLinePos(), token.getLine());
            }
        }
    }

    public void add(TextToken token) {
        rawTokens.add(token);
    }

    public void reset() {
        rawTokens.clear();
    }

    public void printRawTokens() {
        System.out.println(rawTokens);
    }

    public void recogize() {
        for (int i = 0; i < rawTokens.size();) {

            i++;
        }
    }


}
