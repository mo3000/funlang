package indi.suiwenbo.funlang.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

    private static final String[] operator = {
        "+", "-", "*", "/", "+=", "-=", "*=", "/=",
        "=", "==", "!=", "<", ">", "<=", ">=", ">>",
        "<<", "!", "(", ")", "{", "}", "[", "]", ".",
        "=>", ","
    };

    private static final String[] predef = {
        "while", "if", "else", "for", "const", "let",
        "int", "string", "function", "float", "void", "class",
        "interface", "public", "private"
    };

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
    }

    public static boolean isPartOfOp(char c) {
        return opParts.contains(c);
    }
}
