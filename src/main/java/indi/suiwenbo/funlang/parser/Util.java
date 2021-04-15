package indi.suiwenbo.funlang.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

    private static final String[] operator = {
        "+", "-", "*", "/", "+=", "-=", "*=", "/=",
        "=", "==", "!=", "<", ">", "<=", ">=", ">>",
        "<<", "!", "(", ")", "{", "}", "[", "]", ".",
        "=>", ",", ":", "?"
    };

    private static final String[] predef = {
        "while", "if", "else", "for", "const", "let",
        "int", "string", "function", "float", "void", "class",
        "interface", "public", "private", "from", "switch", "case", "return",
        "type"
    };

    public static Set<String> predefs;

    private static final Set<Character> opParts;

    public static final OpTree operators;

    static {
        opParts = new HashSet<>();
        operators = new OpTree();
        for (String str : operator) {
            for (Character c : str.toCharArray()) {
                opParts.add(c);
            }
            operators.setPath(str);
        }
        predefs = new HashSet<>();
        predefs.addAll(Arrays.asList(predef));
    }

    public static boolean isPartOfOp(char c) {
        return opParts.contains(c);
    }

    public static int matchPair(List<TextToken> tokens, int begin) {
        String left = tokens.get(begin).getText();
        int leftCount = 1;
        String right = switch (left) {
            case "(" -> ")";
            case "{" -> "}";
            case "[" -> "]";
            case "<" -> ">";
            default -> throw new RuntimeException("invalid left: `" + left + '`');
        };
        for (int i = begin + 1; i < tokens.size(); i++) {
            var t = tokens.get(i);
            String text = t.getText();
            if (left.equals(text)) {
                leftCount++;
            } else if (right.equals(text)) {
                leftCount--;
                if (leftCount < 0) {
                    throw new ParseError("unexpected " + right, t.getLinePos(), t.getLine());
                } else if (leftCount == 0) {
                    return i;
                }
            }
        }
        if (leftCount > 0) {
            var tt = tokens.get(begin);
            throw new ParseError("{ doesn't close", tt.getLinePos(), tt.getLine());
        } else {
            throw new RuntimeException("shouldn't be here");
        }
    }
}
