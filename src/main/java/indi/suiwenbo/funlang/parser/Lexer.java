package indi.suiwenbo.funlang.parser;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import static indi.suiwenbo.funlang.parser.Util.matchPair;


public class Lexer {

    public final static Pattern IntegerPat = Pattern.compile("^[0-9]+$");
    public final static Pattern FloatPat = Pattern.compile("^[0-9]+\\.[0-9]+$");

    private final List<TextToken> rawTokens;

    public Lexer() {
        rawTokens = new ArrayList<>();
    }

    public void add(TextToken token) {
        rawTokens.add(token);
    }

    public void clear() {
        rawTokens.clear();
    }

    public void printRawTokens() {
        System.out.println(rawTokens);
    }

    public List<TextToken> getRawTokens() {
        return rawTokens;
    }


    public int matchParen(int begin, String left) {
        assert rawTokens.get(begin).getText().equals(left);
        return matchPair(rawTokens, begin);
    }


}
