package indi.suiwenbo.funlang.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

    private static final String[] operator = {
        "+", "-", "*", "/", "+=", "-=", "*=", "/=",
        "=", "==", "!=", "<", ">", "<=", ">=", ">>",
        "<<", "!", "(", ")", "{", "}", "[", "]"
    };

    private static Set<Character> opParts;

    static {
        opParts = new HashSet<>();
        for (String str : operator) {
            for (Character c : str.toCharArray()) {
                opParts.add(c);
            }
        }
    }

    public static boolean isPartOfOp(char c) {
        return opParts.contains(c);
    }
}
