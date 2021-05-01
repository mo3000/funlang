package indi.suiwenbo.funlang.ast;

import indi.suiwenbo.funlang.parser.PrimitiveValueType;

public class Operator implements AstNode {

    protected final String image;

    public Operator(String image) {
        this.image = image;
    }

    @Override
    public PrimitiveValueType valueType() {
        throw new RuntimeException();
    }

    @Override
    public Object value() {
        return image;
    }

    @Override
    public AstType type() {
        return AstType.Operator;
    }

    public boolean isParen() {
        return false;
    }

    @Override
    public void eval() {
    }

    @Override
    public void parse() {
        throw new RuntimeException();
    }

    public int priority() {
        return switch (image) {
            case "+", "-" -> 0;
            case "*", "/", "%" -> 1;
            default -> throw new RuntimeException();
        };
    }

    public boolean greaterThan(Operator other) {
        if (other == null) return true;
        return priority() > other.priority();
    }
}
