package indi.suiwenbo.funlang.ast;

import indi.suiwenbo.funlang.parser.PrimitiveValueType;

public class Paren extends Operator {

    public Paren(String image) {
        super(image);
    }

    @Override
    public boolean isParen() {
        return true;
    }
}
