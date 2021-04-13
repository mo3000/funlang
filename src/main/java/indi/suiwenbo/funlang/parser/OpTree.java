package indi.suiwenbo.funlang.parser;

import java.util.HashMap;
import java.util.Map;

public class OpTree {

    private final Map<Character, OpTree> tree;

    private String value = null;


    public void setPath(String path) {
        setPath(path, 0);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OpTree() {
        tree = new HashMap<>();
    }

    public void setPath(String path, int index) {
        char c = path.charAt(index);
        boolean isEnd = index == path.length() - 1;
        if (!tree.containsKey(c)) {
            tree.put(c, new OpTree());
        }
        OpTree t = tree.get(c);
        if (isEnd) {
            t.setValue(path);
        } else {
            t.setPath(path, index + 1);
        }
    }

    public boolean has(char c) {
        return tree.containsKey(c);
    }

    public boolean hasNext() {
        return !tree.isEmpty();
    }


    public boolean acceptable() {
        return value != null;
    }

    public String accept() {
        return value;
    }

    public OpTree get(char c) {
        return tree.get(c);
    }
}
